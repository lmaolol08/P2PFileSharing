📡 P2P File Sharing System in Java

A lightweight Peer-to-Peer File Sharing application built with Java Sockets.
Each peer can act as both server (sharing files) and client (downloading files), with a central Tracker to help discover available peers.

✨ Features

🖥️ Tracker Service – maintains a registry of which peer has which file<br>
🤝 Peer-to-Peer Transfer – direct file sharing between peers (no central server needed)<br>
📂 Shared & Downloads Folders – peers host files in shared/ and receive them in downloads/<br>
⚡ Multi-threaded File Server – each peer can handle multiple incoming requests simultaneously<br>
🔍 File Discovery – peers can query the tracker to find available sources<br>
🚀 How It Works<br>

Start the Tracker on a known port (default: 5000).<br>
Launch multiple Peers on different ports (e.g., 6000, 6001).<br>

Each peer:<br>

Registers files with the tracker<br>
Queries tracker for available peers<br>
Requests and downloads files from other peers<br>
Downloaded files are saved in the downloads/ folder.<br>

📸 Example Run<br>
# Start tracker<br>
java -cp src tracker.Tracker<br>

# Start peer1 (port 6000)<br>
java -cp src peer.Peer 6000<br>

# Start peer2 (port 6001)<br>
java -cp src peer.Peer 6001<br>
✅ Peer2 downloads files from Peer1 via the tracker.<br>

🛠️ Tech Stack<br>

☕ Java (Sockets, Multithreading, I/O Streams)<br>
🗂️ Git + GitHub for version control<br>
🐳 Docker (future containerized demo)<br>
🌟 Future Works<br>

Here’s what’s coming soon ⏳:<br>

📜 Interactive CLI → run commands like register, query, download<br>
📦 Chunked Downloads → split files into parts and download from multiple peers simultaneously<br>
🔄 Resume Support → continue from where a failed download stopped<br>
🔑 Secure Transfers → add encryption (AES/RSA) for privacy<br>
✅ Checksum Validation → ensure file integrity with SHA-1/MD5<br>
🎨 JavaFX GUI → simple desktop interface to manage files<br>
☁️ Cloud Demo → deploy tracker and peers across different servers<br>

📌 Repository Roadmap<br>
1️⃣Basic tracker & peer communication<br>
2️⃣Peer-to-Peer file transfer<br>
3️⃣Interactive command line interface<br>
4️⃣Multi-peer chunked downloads<br>
5️⃣Security & validation<br>
6️⃣GUI front-end<br>

🤝 Contributing<br>

Pull requests are welcome. For major changes, please open an issue first to discuss what you’d like to change.
