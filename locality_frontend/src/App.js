import "./App.css";
import Navbar from "./components/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import RouteList from "./Routes/RouteList";

function App() {
  return (
    <>
      <Navbar />
      <RouteList />
    </>
  );
}

export default App;
