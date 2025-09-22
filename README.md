📡 P2P File Sharing System in Java

A lightweight Peer-to-Peer File Sharing application built with Java Sockets.
Each peer can act as both server (sharing files) and client (downloading files), with a central Tracker to help discover available peers.

✨ Features

🖥️ Tracker Service – maintains a registry of which peer has which file
🤝 Peer-to-Peer Transfer – direct file sharing between peers (no central server needed)
📂 Shared & Downloads Folders – peers host files in shared/ and receive them in downloads/
⚡ Multi-threaded File Server – each peer can handle multiple incoming requests simultaneously
🔍 File Discovery – peers can query the tracker to find available sources
🚀 How It Works

Start the Tracker on a known port (default: 5000).
Launch multiple Peers on different ports (e.g., 6000, 6001).

Each peer:

Registers files with the tracker
Queries tracker for available peers
Requests and downloads files from other peers
Downloaded files are saved in the downloads/ folder.

📸 Example Run
# Start tracker
java -cp src tracker.Tracker

# Start peer1 (port 6000)
java -cp src peer.Peer 6000

# Start peer2 (port 6001)
java -cp src peer.Peer 6001
✅ Peer2 downloads files from Peer1 via the tracker.

🛠️ Tech Stack

☕ Java (Sockets, Multithreading, I/O Streams)
🗂️ Git + GitHub for version control
🐳 Docker (future containerized demo)
🌟 Future Works

Here’s what’s coming soon ⏳:

📜 Interactive CLI → run commands like register, query, download
📦 Chunked Downloads → split files into parts and download from multiple peers simultaneously
🔄 Resume Support → continue from where a failed download stopped
🔑 Secure Transfers → add encryption (AES/RSA) for privacy
✅ Checksum Validation → ensure file integrity with SHA-1/MD5
🎨 JavaFX GUI → simple desktop interface to manage files
☁️ Cloud Demo → deploy tracker and peers across different servers

📌 Repository Roadmap
1️⃣Basic tracker & peer communication
2️⃣Peer-to-Peer file transfer
3️⃣Interactive command line interface
4️⃣Multi-peer chunked downloads
5️⃣Security & validation
6️⃣GUI front-end

🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you’d like to change.
