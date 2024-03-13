let ultimoScroll = 1; // inibe primeira retração, impedindo erro
const header = document.getElementById("header");

window.addEventListener("scroll", () => {
    const scroll = window.scrollY || document.documentElement.scrollTop;

    if (scroll > ultimoScroll) {
        // Scrolling down
        // header.style.top = `-${header.offsetHeight}px`;

        header.style.height = '6vh'
    } else {
        // Scrolling up
        // header.style.top = "0";

        header.style.height = '11vh'

    }

    ultimoScroll = scroll;
});