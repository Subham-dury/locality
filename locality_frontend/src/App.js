import React, {useEffect} from "react";
import "./App.css";
import Navbar from "./components/common/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import RouteList from "./routes/RouteList";
import Footer from "./components/common/Footer";

function App() {

  useEffect(() => {
    
  }, [])

  return (
    <>
      <Navbar />
      <RouteList />
      <Footer />
    </>
  );
}

export default App;
