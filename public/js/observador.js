document.addEventListener("DOMContentLoaded", function () {

    // função ativada quando o elemento é visto
    function handleIntersection(entries, observer) {
        entries.forEach(async entry => {
            if (entry.isIntersecting) {

                
                mensagens.forEach((element, index) => {
                    // Use setTimeout to add the class with a delay for each element
                    setTimeout(() => {
                        element.classList.add("msgVisivel");
                    }, index * 750); // 500 milliseconds delay for each element
                });

                observer.unobserve(entry.target); // Para de observar o elemento
            }
        });
    }

    // Cria o observador
    const observer = new IntersectionObserver(handleIntersection, {
        threshold: 0.5, // Quando for visível 50% do conteúdo
    });

    // // Seleciona as mensagens
    // const mensagens = document.querySelectorAll("#boxChat .mensagem");

    // // Observa cada mensagem 
    // mensagens.forEach(element => {
    //     observer.observe(element);
    // });

    const mensagens = document.querySelectorAll(".mensagem")

    // Seleciona as mensagens
    const box = document.querySelector("#boxChat");
    observer.observe(box);
});