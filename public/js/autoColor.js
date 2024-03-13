var elementos = document.querySelectorAll("#introducao .titulo")
console.log(elementos.length)

let deg = 90

function atualizarGradiente(){
    for (var i = 0 ; i < elementos.length ; i++){
        elementos[i].style.background = `linear-gradient(${deg}deg, rgb(60, 132, 233), rgb(17, 255, 255))`
    }
    deg += 5

    if (deg >= 360)
        deg = deg % 360
    
    setTimeout(atualizarGradiente, 50); // 100ms
}

atualizarGradiente()