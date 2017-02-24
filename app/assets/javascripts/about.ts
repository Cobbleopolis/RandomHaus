/// <reference path="mediacontroller.ts"/>

let mainAbout: JQuery = null;

$(() => {
    mainAbout = $('#main');
    mainAbout.css('min-height', ($(window).outerHeight() - 100) + 'px');
});
