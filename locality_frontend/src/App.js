import "./App.css";
import Navbar from "./components/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import RouteList from "./Routes/RouteList";
import Footer from "./components/Footer";

function App() {
  return (
    <>
      <Navbar />
      <RouteList />
      <Footer />
    </>
  );
}

export default App;
