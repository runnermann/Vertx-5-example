const carouselTrack = document.querySelector('.carousel-track');
const carouselItems = document.querySelectorAll('.carousel-item');

// Clone the items for seamless infinite scroll
carouselItems.forEach(item => {
    const clone = item.cloneNode(true);
    carouselTrack.appendChild(clone);
});
