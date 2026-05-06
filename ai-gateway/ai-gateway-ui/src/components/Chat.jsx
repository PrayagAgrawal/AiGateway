import { useState } from "react";
import API from "../services/api";

export default function Chat() {
  const [prompt, setPrompt] = useState("");
  const [response, setResponse] = useState("");
  const [useRag, setUseRag] = useState(false);

  const handleAsk = async () => {
    const res = await API.post("/api/v1/ai/generate", {
      prompt,
      useRag,
    });

    setResponse(res.data.response);
  };

  return (
    <div>
      <h2>AI Gateway</h2>

      <textarea
        placeholder="Ask something..."
        onChange={(e) => setPrompt(e.target.value)}
      />

      <div>
        <label>
          <input
            type="checkbox"
            checked={useRag}
            onChange={() => setUseRag(!useRag)}
          />
          Use RAG
        </label>
      </div>

      <button onClick={handleAsk}>Ask</button>

      <h3>Response:</h3>
      <p>{response}</p>
    </div>
  );
}