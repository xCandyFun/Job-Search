const express = require('express');
const path = require('path');
const app = express();
require('dotenv').config();
const mysql = require('mysql2');

const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME
});

db.connect(err => {
    if(err){
        console.error('Error connecting to MySQL:', err);
        throw err;
    }
    console.log('Connected to mysqlDB')
});

//app.use(express.static(path.join(__dirname, '../../Html'))); // This one is for the html files

//In the future if you want more pages make sure to add a new app.use . 
//First part is the endpoint example '/Index' and the second part is to found the css file for the html file.  

app.use('/Index', express.static(path.join(__dirname, '../../Pages/HomePage'))); // This one is for the css files

app.use('/Index/api/showWork', express.static(path.join(__dirname, '../../Pages/ShowDBPage'))); // This one is for the css files

 


app.get('/Index', (req, res) =>{
    
    //This is a way to find the html file and show it.
    res.sendFile(path.join(__dirname, '../../Pages/Homepage/index.html'))

});

app.get('/Index/api/showWork', (req, res) =>{

    res.sendFile(path.join(__dirname, '../../Pages/ShowDBPage/showDB.html'))

});
   

app.get('/Index/api/swork', (req,res) =>{
    const sql = 'SELECT * FROM works';

    db.query(sql, (err, results) => {
        if(err){
            return res.status(500).json({ message: 'Error fetching works'});
        }
        res.json(results);
    });
});

app.post('/Index/api/awsdb', (req, res) =>{

});


app.listen(3000, () =>{
    console.log('Server is running on port 3000')
});