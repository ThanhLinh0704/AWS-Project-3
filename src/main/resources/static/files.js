async function fetchFileList() {
    try {
        const response = await fetch("/getfilesfromdb");
        const fileNames = await response.json();

        const fileListElement = document.getElementById("fileList");
        fileListElement.innerHTML = "";

        fileNames.forEach((fileName) => {
            const fileItem = document.createElement("a");
            fileItem.href = `/file-content/${encodeURIComponent(fileName)}`;
            fileItem.className = "item";
            fileItem.textContent = fileName;
            fileItem.target = "_blank"; // Open in new tab
            fileListElement.appendChild(fileItem);
        });
    } catch (error) {
        console.error("Error fetching file list:", error);
    }
}
fetchFileList();
