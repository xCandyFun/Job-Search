const express = require('express');
const path = require('path');
const app = express();

app.use(express.static(path.join(__dirname, '../../Html')));

app.use(express.static(path.join(__dirname, '../../Css')));

app.get('/', (req, res) =>{
    res.sendFile(path.join(__dirname, '../../Html'))
});


app.listen(3000, () =>{
    console.log('Server is running on port 3000')
});