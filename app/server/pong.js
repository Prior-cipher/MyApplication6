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

let ballX=500;
let ballY=500;


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
   

    if(ballX>=curentPosition &&ballX<=curentPosition+350 &&ballY>=height-50)
    {
        ballY=height-49;
        ballYSpeed=-ballYSpeed;
        ballXxpeed+=curentSpeed;
    }
    else if (ballY>height
    )
    {
        ballX=500;
        ballY=height/2;
        ballXxpeed=0;
        ballYSpeed=-30;
        scoreO+=1;
    }
    if(ballX>=enemyPosition &&ballX<=enemyPosition+350 &&ballY<=50)
    {
        ballY=51;
        ballYSpeed=-ballYSpeed;
    }
    else if(ballY<0)
    {

        ballX=500;
        ballY=height/2;
        ballXxpeed=0;
        ballYSpeed=30;
        score1+=1;
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
   
 if(scoreO>7)
 {
  
    parentPort.postMessage({"method":"endgame1",id1,id2,scoreO,score1,"win1":true}) ;
    parentPort.postMessage({"method":"endgame1","id2":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":false});
    clearInterval(timerId);
    process.exit(1);
}
else if(score1>7){
    parentPort.postMessage({"method":"endgame1",id1,id2,scoreO,score1,"win1":false}) ;
    parentPort.postMessage({"method":"endgame1","id2":id1,"id1":id2,"scoreO":score1,"score1":scoreO,"win1":true});
    clearInterval(timerId);
    process.exit(1);
}
 
  

    
    parentPort.postMessage({"method":"gamePongStat",id1,ballX,ballY,curentPosition,enemyPosition,scoreO,score1});
    parentPort.postMessage({"method":"gamePongStat","id1":id2,"ballX":1080-ballX,"ballY":1920-ballY,"curentPosition":enemyPosition,"enemyPosition":curentPosition,"scoreO":score1,"score1":scoreO});
}
