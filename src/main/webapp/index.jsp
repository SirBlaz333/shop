<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Shop</title>
    <!--

    Template 2103 Central

	http://www.tooplate.com/view/2103-central

    -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400">
    <!-- Google web font "Open Sans" -->
    <link rel="stylesheet" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css" />
    <link rel="stylesheet" href="css/tooplate-style.css">
    <!-- tooplate style -->

    <script>
        var renderPage = true;

        if (navigator.userAgent.indexOf('MSIE') !== -1
            || navigator.appVersion.indexOf('Trident/') > 0) {
            /* Microsoft Internet Explorer detected in. */
            alert("Please view this in a modern browser such as Chrome or Microsoft Edge.");
            renderPage = false;
        }
    </script>

</head>

<body>
    <!-- Loader -->
    <div id="loader-wrapper">
        <div id="loader"></div>
        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>
    </div>
    <mylib:menu-panel/>
    <div class="container">
        <section class="tm-section-head" id="top">
            <header id="header" class="text-center tm-text-gray">
                <h1>SHOP</h1>
            </header>
        </section>

        <section class="row" id="tm-section-1">
            <div class="col-lg-12 tm-slider-col">
                <div class="tm-img-slider">
                    <div class="tm-img-slider-item" href="img/gallery-img-01.jpg">
                        <p class="tm-slider-caption">Buy CPU whatever you want</p>
                        <img src="img/gallery-img-01.jpg" alt="Image" class="tm-slider-img">
                    </div>
                    <div class="tm-img-slider-item" href="img/gallery-img-02.jpg">
                        <p class="tm-slider-caption">Buy CPU whatever you want</p>
                        <img src="img/gallery-img-02.jpg" alt="Image" class="tm-slider-img">
                    </div>
                    <div class="tm-img-slider-item" href="img/gallery-img-03.jpg">
                        <p class="tm-slider-caption">Buy CPU whatever you want</p>
                        <img src="img/gallery-img-03.jpg" alt="Image" class="tm-slider-img">
                    </div>
                </div>
            </div>
        </section>

        <section class="tm-section-2 tm-section-mb" id="tm-section-2">
            <div class="row">
                <div class="col-xl-4 col-lg-4 col-md-6 mb-lg-0 mb-md-5 mb-5 pr-md-5">
                    <header class="text-center">
                        <i class="fa fa-4x fa-power-off pl-5 pb-5 pr-5 pt-2"></i>
                    </header>

                    <h2>CPU</h2>
                    <p>The CPU is the core component that defines a computing device, and while it is of critical importance,
                        the CPU can only function alongside other hardware. </p>

                    <a href="#tm-section-3" class="btn tm-btn-pad-2 float-right">Learn More</a>
                </div>
                <div class="col-xl-4 col-lg-4 col-md-6 mb-lg-0 mb-md-5 mb-5 pr-md-5">
                    <header class="text-center">
                        <i class="fa fa-4x fa-bolt pl-5 pb-5 pr-5 pt-2"></i>
                    </header>
                    <h2>How are CPUs built?</h2>
                    <p> CPUs are built by placing billions of microscopic transistors onto a single computer chip.
                        Those transistors allow it to make the calculations it needs to run programs that are stored on your system’s memory.
                    </p>
                </div>

                <div class="col-xl-4 col-lg-4 col-md-12">
                    <h2>Why us?</h2>
                    <p> We provide everything, that you need. Our market has:</p>
                    <p>+ large selections</p>
                    <p>+ low prices</p>
                    <p>+ good employees</p>
                </div>
            </div>
        </section>

        <section class="tm-section-4 tm-section-mb" id="tm-section-4">
            <div class="row">

            </div>
        </section>

        <section class="tm-section-3 tm-section-mb" id="tm-section-3">
            <div class="row">
                <div class="col-md-6 tm-mb-sm-4 tm-2col-l">
                    <div class="image" align="center">
                        <img src="img/tm-img-1.jpg" class="img-fluid" />
                    </div>
                    <div class="tm-box-3">
                        <h2>Intel Core i7</h2>
                        <p> An Intel Corei7 is the fastest version of the Intel processor for consumer-end computers and devices.
                            Like the Intel Corei5, the Corei7 is embedded with Intel Turbo Boost Technology.
                            The Intel Corei7 is available in two- to six-core varieties,
                            and can support up to 12 different threads simultaneously.</p>
                        <div class="text-center">
                            <a href="products.html" class="btn btn-big">Details</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 tm-2col-r">
                    <div class="image" align="center">
                        <img src="img/tm-img-2.jpg" class="img-fluid" />
                    </div>
                    <div class="tm-box-3">
                        <header>
                            <h2>AMD Ryzen 5</h2>
                        </header>
                        <p> Ryzen 5 (pronounced Rye-Zen Five) is a family of mid-range performance 64-bit quad and hexa-cores x86
                            microprocessors introduced by AMD in March of 2017. Ryzen 5 is based on the Zen microarchitecture
                            and is manufactured on GF's 14 nm process. Ryzen 5 is marketed toward the mid-range performance market.</p>
                        <div class="text-center">
                            <a href="products.html" class="btn btn-big">Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <footer class="mt-5">
            <p class="text-center">Copyright © 2018 Shop - Design:
                <a rel="nofollow" href="http://www.tooplate.com/view/2103-central" target="_parent" class="tm-text-black">Central</a>
            </p>
        </footer>
    </div>

    <!-- load JS files -->
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script src="js/popper.min.js"></script>
    <!-- https://popper.js.org/ -->
    <script src="js/bootstrap.min.js"></script>
    <!-- https://getbootstrap.com/ -->
    <script type="text/javascript" src="slick/slick.min.js"></script>
    <!-- Slick Carousel -->

    <script>
        function setCarousel() {
            var slider = $('.tm-img-slider');

            if (slider.hasClass('slick-initialized')) {
                slider.slick('destroy');
            }

            if ($(window).width() > 991) {
                // Slick carousel
                slider.slick({
                    autoplay: true,
                    fade: true,
                    speed: 800,
                    infinite: true,
                    slidesToShow: 1,
                    slidesToScroll: 1
                });
            } else {
                slider.slick({
                    autoplay: true,
                    fade: true,
                    speed: 800,
                    infinite: true,
                    slidesToShow: 1,
                    slidesToScroll: 1
                });
            }
        }

        $(document).ready(function () {
            if (renderPage) {
                $('body').addClass('loaded');
            }

            setCarousel();

            $(window).resize(function () {
                setCarousel();
            });

            // Close menu after link click
            $('.nav-link').click(function () {
                $('#mainNav').removeClass('show');
            });

            // https://css-tricks.com/snippets/jquery/smooth-scrolling/
            // Select all links with hashes
            $('a[href*="#"]')
                // Remove links that don't actually link to anything
                .not('[href="#"]')
                .not('[href="#0"]')
                .click(function (event) {
                    // On-page links
                    if (
                        location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')
                        &&
                        location.hostname == this.hostname
                    ) {
                        // Figure out element to scroll to
                        var target = $(this.hash);
                        target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                        // Does a scroll target exist?
                        if (target.length) {
                            // Only prevent default if animation is actually gonna happen
                            event.preventDefault();
                            $('html, body').animate({
                                scrollTop: target.offset().top + 1
                            }, 1000, function () {
                                // Callback after animation
                                // Must change focus!
                                var $target = $(target);
                                $target.focus();
                                if ($target.is(":focus")) { // Checking if the target was focused
                                    return false;
                                } else {
                                    $target.attr('tabindex', '-1'); // Adding tabindex for elements not focusable
                                    $target.focus(); // Set focus again
                                };
                            });
                        }
                    }
                });
        });
    </script>

</body>

</html>