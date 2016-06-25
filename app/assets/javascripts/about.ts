/// <reference path="mediacontroller.ts"/>

var main:JQuery = null;

$(function () {
    main = $('#main');
    main.css('min-height', ($(window).outerHeight() - 100) + 'px');
});
