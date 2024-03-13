const express = require('express')
const app = express()


// enviar HTML

app.get('/',(req,res) => {
    // res.sendFile("../public/html/index.html")
    console.log(path.join(__dirname, 'public', 'html', 'index.html') + "AAAAAAAAAA")
    // res.sendFile(path.join(__dirname, 'public', 'html', 'index.html'))
})


app.listen(9834, () => {
    let data = new Date()
    console.log('Servidor Node iniciado em: ' + data )
})

// node 'Z:\8 - Pessoal\Portal da Matemática/src/index.js'