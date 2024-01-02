const activePage = window.location.pathname;

const navLinks = document.querySelectorAll('.navbar-nav a').forEach(link => {
    console.log(link.href);
    if(link.href.includes(`${activePage}`)) {
        link.classList.add('active');
    }
})