import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Home() {

   const [hello, setHello] = useState('')

    useEffect(() => {
        axios.get('/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

    return (
        <div>
            백엔드에서 가져온 데이터입니다 : {hello}
            <h1>Hello</h1>
        </div>
        );
    }
export default Home;