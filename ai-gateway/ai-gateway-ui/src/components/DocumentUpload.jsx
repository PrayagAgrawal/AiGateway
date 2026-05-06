import { useState } from "react";
import API from "../services/api";

export default function DocumentUpload() {
  const [content, setContent] = useState("");

  const handleUpload = async () => {
    await API.post("/api/v1/docs/add", content, {
      headers: { "Content-Type": "application/json" },
    });

    alert("Document added!");
  };

  return (
    <div>
      <h3>Add Document</h3>
      <textarea
        placeholder="Enter document..."
        onChange={(e) => setContent(e.target.value)}
      />
      <button onClick={handleUpload}>Upload</button>
    </div>
  );
}