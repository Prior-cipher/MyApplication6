const {BroadcastChannel, workerData, parentPort, isMainThread } = require('worker_threads');



let time = Date.now();
let id1=workerData.clientId1;
let id2=workerData.clientId2;
// let id1=0;
// let id2=1;
let curentSpeed = 0;
let enemySpeed=0;

let curentPosition = 350;
let enemyPosition=350;

let ballX=525;
let ballY=200;
let hod=true;

let ballXxpeed=0;
let ballYSpeed=30;

let scoreO=0;

let score1=0;

let speed=0;

let speedEnemy=0;

let height=1920;
var bc= new BroadcastChannel(workerData.name);



bc.onmessage = event => 
{
   
if(event.data.id==id1)
{
    
    speed=event.data.speed;
}
else
{
    speedEnemy=-event.data.speed;
}
  
}
let timerId = setInterval(takt,50);

class Bricks{
    constructor(x,y)
    {
        this.y=y;
        this.x=x;
        this.c=getRandomInt(7)
    }

}



let BrickList=[];
let BrickListReverser=[];

for (let i = 3; i < 7; i++) {
    for (let j = 1; j < 6; j++) 
    {
        BrickList.push(new Bricks( j * 150+80, i * 100+580));
        BrickListReverser.push(new Bricks( 1080-(j * 150+80), 1920-(i * 100+580)));
    }
}
function takt()
{

    speed= speed * 0.5;
    speedEnemy= speedEnemy * 0.5;

    curentSpeed+=speed;
    curentSpeed *= 0.95 ;
    
    enemySpeed+=speedEnemy;
    enemySpeed *= 0.95 ;


    ballX+=ballXxpeed;
    ballY+=ballYSpeed;

    

    if (curentPosition+speed <= 0)
    {
        curentPosition = 0;
        curentSpeed = 0;
    } else if (curentPosition+speed >= 730)
    {
        curentPosition = 730;
        curentSpeed = 0;
    }
    curentPosition=curentPosition+curentSpeed;


    if (enemyPosition+speedEnemy <= 0)
    {
        enemyPosition = 0;
        enemySpeed = 0;
    } else if (enemyPosition+speedEnemy >= 730)
    {
        enemyPosition = 730;
        enemySpeed = 0;
    }
    enemyPosition+=enemySpeed;
   
    for (let i = 0; i < BrickList.length; i++)
    {
        let b = BrickList[i];
        if (brickReflection(b.x, b.y)) 
        {
            BrickList.splice(i, 1);
            BrickListReverser.splice(i, 1);
            if(hod){
                scoreO = scoreO + 100;
            }
            else{
                score1 = score1 + 100;
            }
           
            break;
        }
    }



    if((ballX>=curentPosition &&ballX<=curentPosition+350||ballX+50>=curentPosition &&ballX<=curentPosition+350) &&ballY>=height-50)
    {
        ballY=height-49;
        ballYSpeed=-ballYSpeed;
        ballXxpeed+=curentSpeed;
        hod=true;
    }
    else if (ballY>height)
    {
        ballX=525;
        ballY=1920-250;
        ballXxpeed=0;
        ballYSpeed=-30;
        score1-=1000;
    }
    if((ballX>=enemyPosition &&ballX<=enemyPosition+350 || ballX+50>=enemyPosition && ballX+50<=enemyPosition+350 ) && ballY<=50)
    {
        ballY=51;
        ballXxpeed+=enemySpeed;
        ballYSpeed=-ballYSpeed;
        hod=false;
    }
    else if(ballY<0)
    {

        ballX=525;
        ballY=1920-250;
        ballXxpeed=0;
        ballYSpeed=30;
        scoreO-=1000;
    }
    if(ballX<=0)
    {
        ballX=0;
        ballXxpeed=-ballXxpeed;
    }
    else if(ballX>=1030)
    {
        ballX=1030;
        ballXxpeed=-ballXxpeed;

    }


   
 if(scoreO>7000)
 {
  
    parentPort.postMessage({"method":"endgame4","id":id2,scoreO,score1,"win1":true}) ;
    parentPort.postMessage({"method":"endgame4","id":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":false});
    clearInterval(timerId);
    process.exit(1);
}
else if(score1>7000){
    parentPort.postMessage({"method":"endgame4","id":id2,scoreO,score1,"win1":false}) ;
    parentPort.postMessage({"method":"endgame4","id":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":true});
    clearInterval(timerId);
    process.exit(1);
}
 
if(BrickList.length==0)
{
 
    if(scoreO>score1)
 {
  
    parentPort.postMessage({"method":"endgame4","id":id2,scoreO,score1,"win1":true}) ;
    parentPort.postMessage({"method":"endgame4","id":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":false});
    clearInterval(timerId);
    process.exit(1);
}
else {
    parentPort.postMessage({"method":"endgame4","id":id2,scoreO,score1,"win1":false}) ;
    parentPort.postMessage({"method":"endgame4","id":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":true});
    clearInterval(timerId);
    process.exit(1);
}
}


    
    parentPort.postMessage({"method":"gameArcStat","id":id1,ballX,ballY,curentPosition,enemyPosition,scoreO,score1,BrickList});
    parentPort.postMessage({"method":"gameArcStat","id":id2,"ballX":1080-ballX,"ballY":1920-ballY,"curentPosition":730-enemyPosition,"enemyPosition":730-curentPosition,"scoreO":score1,"score1":scoreO,"BrickList":BrickListReverser});
}

function changeDirection(xBrick,yBrick)
{


    let k=-((yBrick+25)-(ballY+25))/((ballX+25)-(xBrick+50))
   

    let b= -(((xBrick+50)*(ballY+25))-((ballX+25)*(yBrick+25)))/((ballX+25)-(xBrick+50))

    

   
    console.log(`${(yBrick+50-b)/k} -${xBrick}`);
    console.log(`${(yBrick)/k-b} -${xBrick}`);
    console.log(`${k*(xBrick+100)+b} -${yBrick}`);
    console.log(`${k*(xBrick)+b} -${yBrick}`);
    if((yBrick+50-b)/k>xBrick &&(yBrick+50-b)/k<xBrick+100)
    {
        ballYSpeed=-ballYSpeed;
        return;
    }
    if((yBrick-b)/k>xBrick &&(yBrick-b)/k<xBrick+100)
    {
        ballYSpeed=-ballYSpeed;
        return;
    }
    if(k*(xBrick+100)+b>yBrick&&k*(xBrick+100)+b<k*(xBrick+100)+b+50)
    {
        ballXxpeed=-ballXxpeed;
        return;
    }
    if(k*(xBrick)+b>yBrick&&k*(xBrick)+b<yBrick+50)
    {
        ballXxpeed=-ballXxpeed;
        return;
    }
}

function brickCLose( ax,  ay) 
{
  
    if(ballX+50>=ax  &&ballX+50<= ax+100&&ballY+50>=ay&&ballY+50<=ay+50)
    {
        return true;
    }else if(ballX+50>=ax  &&ballX+50<= ax+100&&ballY>=ay&&ballY<=ay+50){
        return true;
    }
   
    else if(ballX>=ax  &&ballX<= ax+100&&ballY+50>=ay&&ballY+50<=ay+50){

        return true;
    }
    else if(ballX>=ax  &&ballX<= ax+100&&ballY>=ay&&ballY<=ay+50)
    {
        return true;
    }
    else
    {
        return false;
}
   
}

function brickReflection( xBrick,  yBrick)
{
    if (brickCLose(xBrick, yBrick))
    {
        changeDirection(xBrick,yBrick);
        return true;
    } else 
    return false;
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }