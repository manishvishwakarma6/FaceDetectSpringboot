let db;

const db_name = "imageStore";
const db_version = 1;
const store_name = "images";  

//  Open IndexedDB
const request = indexedDB.open(db_name, db_version);
request.onupgradeneeded = function (event) {
  db = event.target.result;
  if (!db.objectStoreNames.contains(store_name)) {
    db.createObjectStore(store_name, { autoIncrement: true });
  }
};

request.onsuccess = function (event) {
  db = event.target.result;
  if (navigator.onLine && localStorage.getItem("sync_allowed") === "true") {
    syncImagesToServer();
  }
};

request.onerror = function () {
  console.error("Failed to open IndexedDB");
};

//  Track offline refresh — disable sync
window.addEventListener("load", function () {
  if (!navigator.onLine) {
    localStorage.setItem("sync_allowed", "false");
  }
});

// Enable sync when internet returns + auto refresh page after sync
window.addEventListener("online", function () {
  const syncAllowed = localStorage.getItem("sync_allowed");
  if (syncAllowed === "true") {
    syncImagesToServer();
    setTimeout(() => location.reload(), 3000);
  } else {
    localStorage.setItem("sync_allowed", "true");
    console.log("Internet back, sync allowed for next time");
  }
});

//  Handle form submission
document.getElementById("imageForm").addEventListener("submit", function (event) {
  event.preventDefault();
  const input = document.getElementById("file");
  const file = input.files[0];
  if (!file) return;

  if (navigator.onLine) {
    uploadToServer([file]);
	showMsg("Image saved mysql db ");
  } else {
    storeInIndexedDB(file);
    showMsg("Image saved offline");
  }

  input.value = ""; 
});
    
//  Store offline image
function storeInIndexedDB(file) {
  const tx = db.transaction([store_name], "readwrite");
  const store = tx.objectStore(store_name);
  store.add(file);
}

//  Upload to server (single or multiple files)
function uploadToServer(files, callback) {
  const formData = new FormData();
  files.forEach((file, index) => {
    formData.append("images", file, `offline_image_${index}.jpg`);
  });

  fetch("/uploadAll", {
    method: "POST",
    body: formData,
  })
    .then(res => res.text())
    .then(data => {
      showMsg("Image(s) uploaded to server");
      if (callback) callback();
    })
    .catch(err => {
      console.error("Upload failed", err);
      showMsg("Upload failed, try again");
    });
}

// Sync all stored images to server in one batch
function syncImagesToServer() {
  if (!db || !navigator.onLine) return;

  const tx = db.transaction([store_name], "readonly");
  const store = tx.objectStore(store_name);
  const getAllRequest = store.getAll();

  getAllRequest.onsuccess = function () {
    const files = getAllRequest.result;
    if (!files || files.length === 0) return;

    uploadToServer(files, () => {
      const delTx = db.transaction([store_name], "readwrite");
      const delStore = delTx.objectStore(store_name);
      delStore.clear();
      showMsg("All offline images uploaded and cleared");
    });
  };

  getAllRequest.onerror = function () {
    console.error("Failed to get files from IndexedDB");
  };
}

// Show status message
function showMsg(msg) {
  const div = document.getElementById("uploading_msg");
  div.textContent = msg;
  setTimeout(() => {
    div.textContent = "";
  }, 3000);
}
