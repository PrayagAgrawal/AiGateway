import { useState } from "react";
import Login from "./components/Login";
import Chat from "./components/Chat";
import DocumentUpload from "./components/DocumentUpload";

function App() {
  const [token, setToken] = useState(null);

  if (!token) {
    return <Login setToken={setToken} />;
  }

  return (
    <div>
      <Chat />
      <DocumentUpload />
    </div>
  );
}

export default App;