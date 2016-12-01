var doge = document.getElementById("frog");
var img = document.getElementById("car");
var car2 = document.getElementById("car2");
var jeep = document.getElementById("jeep");
var lorry = document.getElementById("lorry");
var suv = document.getElementById("suv");
var van = document.getElementById("van");
var taxi = document.getElementById("taxi");
var sportscar = document.getElementById("sportscar");
var longcar = document.getElementById("long");
var truck = document.getElementById("truck");
var forumla = document.getElementById("formula");
var black = document.getElementById("black");
var frame = document.getElementById("frame");
var goal = document.getElementById("goal");
var gamestart = document.getElementById("gamestart");
var gamepause = document.getElementById("gamepause");
var goodjob = document.getElementById("goodjob");
var uccu = document.getElementById("uccu");
var sound = document.getElementById("clicks");
var rush = document.getElementById("rush");
var extreme = document.getElementById("extreme");
var keyboard = document.getElementById("keyboard");
var laugh = document.getElementById("laugh");
var FF7 = document.getElementById("FF7");
var gameflag;
var gameflag2;
var gameflag3;
var gameflag4;
var gameflag5;
var gameflag6;
var game;
var game2;
var game3;
var game4;
var game5;
var game6;


function start(obj) {

    doge.src = 'img/frog.png';

    gamestart.style.visibility = "hidden";
    gamepause.style.visibility = "hidden";
    uccu.style.visibility = "hidden";
    goodjob.style.visibility = "hidden";
    sound.play();

    if (obj == 'start') {
        rush.play();
        extreme.pause();
        clearInterval(gameflag);
        clearInterval(gameflag2);
        clearInterval(gameflag3);
        clearInterval(gameflag4);
        clearInterval(gameflag5);
        clearInterval(gameflag6);
        clearInterval(game);
        clearInterval(game2);
        clearInterval(game3);
        clearInterval(game4);
        clearInterval(game5);
        clearInterval(game6);

        gameflag = setInterval("move()", 15);
        gameflag2 = setInterval("move2()", 15);
        gameflag3 = setInterval("move3()", 15);
        gameflag4 = setInterval("move4()", 15);
        gameflag5 = setInterval("move5()", 15);
        gameflag6 = setInterval("move6()", 15);
        game = setInterval("moveright()", 15);
        game2 = setInterval("moveright2()", 15);
        game3 = setInterval("moveright3()", 15);
        game4 = setInterval("moveright4()", 15);
        game5 = setInterval("moveright5()", 15);
        game6 = setInterval("moveright6()", 15);


        img.style.top = "175px";
        img.style.left = "740px";
        car2.style.left = "370px";
        jeep.style.left = "0px";
        lorry.style.left = "800px";
        suv.style.left = "430px";
        van.style.left = "60px";
        taxi.style.left = "800px";
        sportscar.style.left = "400px";
        longcar.style.left = "0px";
        truck.style.left = "800px";
        formula.style.left = "400px";
        black.style.left = "0px";
        doge.style.top = "547px";
        doge.style.left = "480px";
    } else if (obj == 'continue') {
        rush.play();
        extreme.pause();
        clearInterval(gameflag);
        clearInterval(gameflag2);
        clearInterval(gameflag3);
        clearInterval(gameflag4);
        clearInterval(gameflag5);
        clearInterval(gameflag6);
        clearInterval(game);
        clearInterval(game2);
        clearInterval(game3);
        clearInterval(game4);
        clearInterval(game5);
        clearInterval(game6);

        gameflag = setInterval("move()", 15);
        gameflag2 = setInterval("move2()", 15);
        gameflag3 = setInterval("move3()", 15);
        gameflag4 = setInterval("move4()", 15);
        gameflag5 = setInterval("move5()", 15);
        gameflag6 = setInterval("move6()", 15);
        game = setInterval("moveright()", 15);
        game2 = setInterval("moveright2()", 15);
        game3 = setInterval("moveright3()", 15);
        game4 = setInterval("moveright4()", 15);
        game5 = setInterval("moveright5()", 15);
        game6 = setInterval("moveright6()", 15);

    } else if ('fast') {
        extreme.play();
        rush.pause();
        clearInterval(gameflag);
        clearInterval(gameflag2);
        clearInterval(gameflag3);
        clearInterval(gameflag4);
        clearInterval(gameflag5);
        clearInterval(gameflag6);
        clearInterval(game);
        clearInterval(game2);
        clearInterval(game3);
        clearInterval(game4);
        clearInterval(game5);
        clearInterval(game6);

        gameflag = setInterval("move()", 1);
        gameflag2 = setInterval("move2()", 1);
        gameflag3 = setInterval("move3()", 1);
        gameflag4 = setInterval("move4()", 1);
        gameflag5 = setInterval("move5()", 1);
        gameflag6 = setInterval("move6()", 1);
        game = setInterval("moveright()", 1);
        game2 = setInterval("moveright2()", 1);
        game3 = setInterval("moveright3()", 1);
        game4 = setInterval("moveright4()", 1);
        game5 = setInterval("moveright5()", 1);
        game6 = setInterval("moveright6()", 1);


    }

    document.onkeydown = function moveDoge(eve) {
        keyboard.play();
        switch (eve.keyCode) {
            case 37: //left
                doge.style.left = (doge.offsetLeft - 8 - 5) + "px";
                doge.style.transform = "rotateY(180deg)";
                break;
            case 38: //up
                doge.style.top = (doge.offsetTop - 8 - 5) + "px";
                doge.style.transform = "rotateX(0deg)";
                break;
            case 39: //right
                doge.style.left = (doge.offsetLeft + 5) + "px";
                doge.style.transform = "rotateY(0deg)";
                break;
            case 40: //down
                doge.style.top = (doge.offsetTop + 5) + "px";
                // doge.style.transform = "rotateX(180deg)";
                break;
            default:
                break;

        }
        limit();

        var a1 = goal.offsetTop + goal.offsetHeight;
        var b2 = doge.offsetTop + doge.offsetHeight;


        if (b2 < a1) {

            alert("好樣的恭喜你過關!!!");
            doge.src = 'img/frog011.png';
            gamepass();



        }

    }

}

function pause() {
    document.onkeydown = false;
    clearInterval(gameflag);
    clearInterval(gameflag2);
    clearInterval(gameflag3);
    clearInterval(gameflag4);
    clearInterval(gameflag5);
    clearInterval(gameflag6);
    clearInterval(game);
    clearInterval(game2);
    clearInterval(game3);
    clearInterval(game4);
    clearInterval(game5);
    clearInterval(game6);
    gamestart.style.visibility = "hidden";
    gamepause.style.visibility = "visible";
    uccu.style.visibility = "hidden";
    goodjob.style.visibility = "hidden";
    rush.pause();
    extreme.pause();
    sound.play();


}

function gameover() {
    document.onkeydown = false;
    clearInterval(gameflag);
    clearInterval(gameflag2);
    clearInterval(gameflag3);
    clearInterval(gameflag4);
    clearInterval(gameflag5);
    clearInterval(gameflag6);
    clearInterval(game);
    clearInterval(game2);
    clearInterval(game3);
    clearInterval(game4);
    clearInterval(game5);
    clearInterval(game6);
    uccu.style.visibility = "visible";
    laugh.play();
    rush.pause();
    extreme.pause();

}

function gamepass() {
    document.onkeydown = false;
    clearInterval(gameflag);
    clearInterval(gameflag2);
    clearInterval(gameflag3);
    clearInterval(gameflag4);
    clearInterval(gameflag5);
    clearInterval(gameflag6);
    clearInterval(game);
    clearInterval(game2);
    clearInterval(game3);
    clearInterval(game4);
    clearInterval(game5);
    clearInterval(game6);
    goodjob.style.visibility = "visible";
    FF7.play();
    rush.pause();
    extreme.pause();

}


function again() {


    doge.src = 'img/frog.png';
    document.onkeydown = false;
    clearInterval(gameflag);
    clearInterval(gameflag2);
    clearInterval(gameflag3);
    clearInterval(gameflag4);
    clearInterval(gameflag5);
    clearInterval(gameflag6);
    clearInterval(game);
    clearInterval(game2);
    clearInterval(game3);
    clearInterval(game4);
    clearInterval(game5);
    clearInterval(game6);

    img.style.top = "175px";
    img.style.left = "740px";
    car2.style.left = "370px";
    jeep.style.left = "0px";
    lorry.style.left = "800px";
    suv.style.left = "430px";
    van.style.left = "60px";
    taxi.style.left = "800px";
    sportscar.style.left = "400px";
    longcar.style.left = "0px";
    truck.style.left = "800px";
    formula.style.left = "400px";
    black.style.left = "0px";
    doge.style.top = "547px";
    doge.style.left = "480px";
    gamestart.style.visibility = 'visible';
    gamepause.style.visibility = "hidden";
    uccu.style.visibility = "hidden";
    goodjob.style.visibility = "hidden";
    rush.pause();
    extreme.pause();
    sound.play();

}


function move() {

    var postLeft = parseInt(img.style.left).toString() == "NaN" ? 0 : parseInt(img.style.left);

    img.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(img.style.width) * -1)) {
        img.style.left = frame.clientWidth + "px";
    }

    var t1 = img.offsetTop;
    var l1 = img.offsetLeft;
    var r1 = img.offsetLeft + img.offsetWidth;
    var b1 = img.offsetTop + img.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b1 < t2 || l1 > r2 || t1 > b2 || r1 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }

}

function move2() {

    var postLeft = parseInt(car2.style.left).toString() == "NaN" ? 0 : parseInt(car2.style.left);

    car2.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(car2.style.width) * -1)) {
        car2.style.left = frame.clientWidth + "px";
    }

    var t4 = car2.offsetTop;
    var l4 = car2.offsetLeft;
    var r4 = car2.offsetLeft + car2.offsetWidth;
    var b4 = car2.offsetTop + car2.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b4 < t2 || l4 > r2 || t4 > b2 || r4 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();


    }

}

function move3() {

    var postLeft = parseInt(jeep.style.left).toString() == "NaN" ? 0 : parseInt(jeep.style.left);

    jeep.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(jeep.style.width) * -1)) {
        jeep.style.left = frame.clientWidth + "px";
    }

    var t5 = jeep.offsetTop;
    var l5 = jeep.offsetLeft;
    var r5 = jeep.offsetLeft + jeep.offsetWidth;
    var b5 = jeep.offsetTop + jeep.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b5 < t2 || l5 > r2 || t5 > b2 || r5 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }

}

function move4() {

    var postLeft = parseInt(lorry.style.left).toString() == "NaN" ? 0 : parseInt(lorry.style.left);

    lorry.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(lorry.style.width) * -1)) {
        lorry.style.left = frame.clientWidth + "px";
    }

    var t8 = lorry.offsetTop;
    var l8 = lorry.offsetLeft;
    var r8 = lorry.offsetLeft + lorry.offsetWidth;
    var b8 = lorry.offsetTop + lorry.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b8 < t2 || l8 > r2 || t8 > b2 || r8 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }

}

function move5() {

    var postLeft = parseInt(suv.style.left).toString() == "NaN" ? 0 : parseInt(suv.style.left);

    suv.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(suv.style.width) * -1)) {
        suv.style.left = frame.clientWidth + "px";
    }

    var t8 = suv.offsetTop;
    var l8 = suv.offsetLeft;
    var r8 = suv.offsetLeft + suv.offsetWidth;
    var b8 = suv.offsetTop + suv.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b8 < t2 || l8 > r2 || t8 > b2 || r8 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }

}

function move6() {

    var postLeft = parseInt(van.style.left).toString() == "NaN" ? 0 : parseInt(van.style.left);

    van.style.left = (postLeft - 1) + "px";

    if (postLeft == (parseInt(van.style.width) * -1)) {
        van.style.left = frame.clientWidth + "px";
    }

    var t8 = van.offsetTop;
    var l8 = van.offsetLeft;
    var r8 = van.offsetLeft + van.offsetWidth;
    var b8 = van.offsetTop + van.offsetHeight;

    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    if (b8 < t2 || l8 > r2 || t8 > b2 || r8 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }

}


function moveright() {
    var postRight = parseInt(taxi.style.left).toString() == "NaN" ? 0 : parseInt(taxi.style.left);
    taxi.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        taxi.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t3 = taxi.offsetTop;
    var l3 = taxi.offsetLeft;
    var r3 = taxi.offsetLeft + taxi.offsetWidth;
    var b3 = taxi.offsetTop + taxi.offsetHeight;

    if (b3 < t2 || l3 > r2 || t3 > b2 || r3 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function moveright2() {
    var postRight = parseInt(sportscar.style.left).toString() == "NaN" ? 0 : parseInt(sportscar.style.left);
    sportscar.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        sportscar.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t6 = sportscar.offsetTop;
    var l6 = sportscar.offsetLeft;
    var r6 = sportscar.offsetLeft + sportscar.offsetWidth;
    var b6 = sportscar.offsetTop + sportscar.offsetHeight;

    if (b6 < t2 || l6 > r2 || t6 > b2 || r6 < l2) {

    } else {

        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function moveright3() {
    var postRight = parseInt(longcar.style.left).toString() == "NaN" ? 0 : parseInt(longcar.style.left);
    longcar.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        longcar.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t7 = longcar.offsetTop;
    var l7 = longcar.offsetLeft;
    var r7 = longcar.offsetLeft + longcar.offsetWidth;
    var b7 = longcar.offsetTop + longcar.offsetHeight;

    if (b7 < t2 || l7 > r2 || t7 > b2 || r7 < l2) {

    } else {


        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function moveright4() {
    var postRight = parseInt(truck.style.left).toString() == "NaN" ? 0 : parseInt(truck.style.left);
    truck.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        truck.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t7 = truck.offsetTop;
    var l7 = truck.offsetLeft;
    var r7 = truck.offsetLeft + truck.offsetWidth;
    var b7 = truck.offsetTop + truck.offsetHeight;

    if (b7 < t2 || l7 > r2 || t7 > b2 || r7 < l2) {

    } else {

        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function moveright5() {
    var postRight = parseInt(formula.style.left).toString() == "NaN" ? 0 : parseInt(formula.style.left);
    formula.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        formula.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t7 = formula.offsetTop;
    var l7 = formula.offsetLeft;
    var r7 = formula.offsetLeft + formula.offsetWidth;
    var b7 = formula.offsetTop + formula.offsetHeight;

    if (b7 < t2 || l7 > r2 || t7 > b2 || r7 < l2) {

    } else {

        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function moveright6() {
    var postRight = parseInt(black.style.left).toString() == "NaN" ? 0 : parseInt(black.style.left);
    black.style.left = (postRight + 1) + "px";
    if (postRight == parseInt(frame.clientWidth)) {
        black.style.left = '-200px';
    }
    var t2 = doge.offsetTop;
    var l2 = doge.offsetLeft;
    var r2 = doge.offsetLeft + doge.offsetWidth;
    var b2 = doge.offsetTop + doge.offsetHeight;

    var t7 = black.offsetTop;
    var l7 = black.offsetLeft;
    var r7 = black.offsetLeft + black.offsetWidth;
    var b7 = black.offsetTop + black.offsetHeight;

    if (b7 < t2 || l7 > r2 || t7 > b2 || r7 < l2) {

    } else {

        alert("哈哈哈哈!!!你看看你~~");
        doge.src = 'img/keroro.jpg';
        gameover();

    }
}

function limit() {
    var doc = [document.getElementById("frame").clientWidth, document.getElementById("frame").clientHeight]
        //防止左側溢出 
    doge.offsetLeft <= 0 && (doge.style.left = 0);
    //防止頂部溢出 
    doge.offsetTop <= 0 && (doge.style.top = 0);
    //防止右側溢出 
    doc[0] - doge.offsetLeft - doge.offsetWidth <= 0 && (doge.style.left = doc[0] - doge.offsetWidth + "px");
    //防止底部溢出 
    doc[1] - doge.offsetTop - doge.offsetHeight <= 0 && (doge.style.top = doc[1] - doge.offsetHeight + "px")
}
