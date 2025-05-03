var swiper = new Swiper(".mySwiper", {
    speed: 300,
    pagination: {
        el: ".ref-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".ref-next",
        prevEl: ".ref-prev",
    },
    breakpoints: {
        // when window width is >= 320px
        380: {
            slidesPerView: 1,
            spaceBetween: 16,
        },
        480: {
            slidesPerView: 2,
            spaceBetween: 24,
        },
        768: {
            slidesPerView: 2.3,
            spaceBetween: 24,
        },
        980: {
            slidesPerView: 2.4,
            spaceBetween: 24,
        },
        1364: {
            slidesPerView: 3.4,
            spaceBetween: 24,
        },
        1918: {
            slidesPerView: 5,
            spaceBetween: 24,
        }
    }
});
var swiper = new Swiper(".swiperTwo", {
    speed: 300,
    pagination: {
        el: ".vdo-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".vdo-next",
        prevEl: ".vdo-prev",
    },
    breakpoints: {
        380: {
            slidesPerView: 1,
            spaceBetween: 16,
        },
        480: {
            slidesPerView: 2,
            spaceBetween: 24,
        },
        768: {
            slidesPerView: 1.4,
            spaceBetween: 24,
        },
        980: {
            slidesPerView: 2,
            spaceBetween: 24,
        },
        1364: {
            slidesPerView: 3.2,
            spaceBetween: 24,
        },
        1918: {
            slidesPerView: 4,
            spaceBetween: 24,
        }
    }
});
