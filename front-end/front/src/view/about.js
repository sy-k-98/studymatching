import React, { useEffect, useState } from "react";
import axios from "axios";
import HelloList from "../components/HelloList";

function About() {
  const [hello, setHello] = useState("");

  useEffect(() => {
    axios
      .get("/api/categories")
      .then((response) => setHello(response.data))
      .catch((error) => console.log(error));
  }, []);
  return (
    <div>
      백엔드에서 가져온 데이터입니다 : {hello.success}
      <h1>Hello</h1>
      <HelloList hello={hello} />
    </div>
  );
}
export default About;
