const {BroadcastChannel, workerData, parentPort, isMainThread } = require('worker_threads');

var bc= new BroadcastChannel(workerData.name);

let id1=workerData.clientId1;
let id2=workerData.clientId2;
let score1= 0;
let score0=0;

bc.onmessage = event => 
{
   
if(event.data.id==id1)
{
    if(event.data.command=="turnLeft")
    {
            checkMoveLeft();
    }
    else if(event.data.command=="rotate")
    {
        checkRotation()
    }
    else
    {
        checkMoveRight();
    }
   
}
else
{
    if(event.data.command=="turnLeft")
    {
            checkMoveLeft2();
    }
    else if(event.data.command=="rotate")
    {
        checkRotation2()
    }
    else
    {
        checkMoveRight2();
    }
}
  
}
let blocks =[] ;
let blocksColor=[];
let currentBrick;
let nextBrick;

let blocks2 =[] ;
let blocksColor2=[];
let currentBrick2;
let nextBrick2;


let scoreINc=[0,100 ,   300 ,  700 , 1500];
let BrickType =[    "SQUARE","L_TYPE","LINE","T_TYPE","Z_TYPE"]


let Color=["#FF0000","#008000","#0000FF","#800080","none"];
  
  





    function  add( A,  B) 
    {
        return  {y:A.y + B.y,x:A.x + B.x};
    }

    function  sub( A,  B) {
        return   {y:A.y - B.y, x:A.x - B.x};
    }

    function rotateAntiClock( X) 
    {

        return  {y:-X.x,x: X.y};
    }

    function isEqual( A,  B) 
    {

        return A.y == B.y && A.x == B.x;
    }

    

class Brick {
 

    constructor ( type) 
    {
        this.type = type;
        this.state = 1;
        this.color=Color[getRandomInt(4)];
        switch (type) {
            case "SQUARE":
                this.coordinates = 
                [
                        {y:0,x:5},
                        {y:1,x:5},
                        {y:1,x:6},
                        {y:0,x:6}
                      
        ];

                break;


            case "L_TYPE":
                this.coordinates = 
                [
                    {y:0,x:6},
                    {y:0,x:5},
                    {y:1,x:6},
                    {y:2,x:6}
                  
    ];

                break;
            case "T_TYPE":
                this.coordinates =  [
                    {y:1,x:5},
                    {y:0,x:5},
                    {y:1,x:6},
                    {y:2,x:6}
                  
    ];
                 

                break;
            case "Z_TYPE":
                this.coordinates =  [
                    {y:1,x:6},
                    {y:1,x:5},
                    {y:0,x:5},
                    {y:2,x:6}
                  
    ];
                

                break;

            case "LINE":
                this.coordinates =  [
                    {y:1,x:5},
                    {y:0,x:5},
                    {y:2,x:5},
                    {y:3,x:5}
                  
    ];

                

                break;
        }

    }
     Down() {
        for (let coord of this.coordinates) 
        {
            coord.y = coord.y + 1;
        }
    }

     MoveLeft() {
        for (let coord of this.coordinates) 
        {
            coord.x = coord.x - 1;
        }
    }

     MoveRight() {
        for (let coord of this.coordinates) 
        {
            coord.x = coord.x + 1;
        }
    }

     Rotate() {
        if (this.type == "SQUARE") 
        {
            return;
        }
        let center = this.coordinates[0];

        for (let i = 0; i < this.coordinates.length; i++) 
        {
            let before = sub(this.coordinates[i], center);
            this.coordinates[i] = add(rotateAntiClock(before), center);
        }


    }
}

    


function Game() 
{

   
   

    for (let i = 0; i < 20; i++) 
    {
        blocks[i]=[];
        blocksColor[i]=[];
        blocks2[i]=[];
        blocksColor2[i]=[];
        for (let j = 0; j < 10; j++) 
        {
            blocksColor[i][j]="none";
            blocks[i][j] = false;

            blocksColor2[i][j]="none";
            blocks2[i][j] = false;
        }


    }

    if (nextBrick == null)
     {

        nextBrick =new Brick(getRandomBrick()) ;
        currentBrick = new Brick(nextBrick.type);
        nextBrick =new Brick(getRandomBrick()) ;
    }

    if (nextBrick2 == null)
     {

        nextBrick2 =new Brick(getRandomBrick()) ;
        currentBrick2 = new Brick(nextBrick2.type);
        nextBrick2 =new Brick(getRandomBrick()) ;
    }


}

function takt() 
{
    placing();
    placing2();
    if(checkWIn())
    {
        parentPort.postMessage({"method":"endgame3","id":id1,"win1":false,score0}) ;
        parentPort.postMessage({"method":"endgame3","id":id2,"win1":true,"score0":score1}) ;
        process.exit(1);
    }
    if(checkWIn2())
    {
        parentPort.postMessage({"method":"endgame3","id":id1,"win1":true}) ;
        parentPort.postMessage({"method":"endgame3","id":id2,score0,"win1":false}) ;
        process.exit(1);
    }
    currentBrick.Down();
    currentBrick2.Down();
    parentPort.postMessage({"method":"gameTetrisInfo","id":id1,currentBrick,nextBrick,blocks,blocksColor,score0,score1});
    parentPort.postMessage({"method":"gameTetrisInfo","id":id2,"currentBrick":currentBrick2,"nextBrick":nextBrick2,"blocks":blocks2,"blocksColor":blocksColor2,"score0":score1,"score1":score0});
}

function checkWIn()
{
    if(score0>=5000){
        return true;
    }
    for (let  c of currentBrick.coordinates) 
    {
        if ( blocks[c.y][c.x]) 
        {
            
          return true;
        }
    }
    return false;
}

function checkWIn2()
{

    if(score1>=5000){
        return true;
    }
    for (let  c of currentBrick2.coordinates) 
    {
        if ( blocks2[c.y][c.x]) 
        {
            
          return true;
        }
    }
    return false;
}
function downingBlock( line) 
{


    

    for (let i = line; i > 0; i--) 
    {
        for (let j = 0; j < 10; j++) {
            blocks[i][j] = blocks[i - 1][j];
        }
    }
    for (let j = 0; j < 10; j++)
    {
        blocks[0][j] = false;
    }

}

function downingBlock2( line) 
{


    

    for (let i = line; i > 0; i--) 
    {
        for (let j = 0; j < 10; j++) {
            blocks2[i][j] = blocks2[i - 1][j];
        }
    }
    for (let j = 0; j < 10; j++)
    {
        blocks2[0][j] = false;
    }

}

function placing() {
    for (let  c of currentBrick.coordinates)
     {
        if ((c.y + 1) > 19 || blocks[c.y + 1][c.x] == true)
         {
            for (let  cInner of currentBrick.coordinates) 
            {
                blocks[cInner.y][cInner.x] = true;
                blocksColor[cInner.y][cInner.x]=currentBrick.color
            }
            checkLine();
            nextBrick1();

            break;
        }
    }
}

function placing2() {
    for (let  c of currentBrick2.coordinates)
     {
        if ((c.y + 1) > 19 || blocks2[c.y + 1][c.x] == true)
         {
            for (let  cInner of currentBrick2.coordinates) 
            {
                blocks2[cInner.y][cInner.x] = true;
                blocksColor2[cInner.y][cInner.x]=currentBrick2.color
            }
            checkLine2();
            nextBrick21();

            break;
        }
    }
}


function checkMoveLeft()
{
     check = true;
    for (let  c of currentBrick.coordinates) 
    {
        if (c.x - 1 < 0 || blocks[c.y][c.x - 1]) 
        {
            check = false;
            break;
        }
    }

    if (check) 
    {
        currentBrick.MoveLeft();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id1,currentBrick,nextBrick,blocks,blocksColor,score0,score1});
    }


}

function checkMoveLeft2()
{
     check = true;
    for (let  c of currentBrick2.coordinates) 
    {
        if (c.x - 1 < 0 || blocks2[c.y][c.x - 1]) 
        {
            check = false;
            break;
        }
    }

    if (check) 
    {
        currentBrick2.MoveLeft();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id2,"currentBrick":currentBrick2,"nextBrick":nextBrick2,"blocks":blocks2,"blocksColor":blocksColor2,"score0":score1,"score1":score0});
    }


}

function checkMoveRight()
{

    let check = true;
    for (let  c of currentBrick.coordinates) 
    {
        if ((c.x + 1) >= 10 || blocks[c.y][c.x + 1]) 
        {
            check = false;
            break;
        }
    }

    if (check) {
        currentBrick.MoveRight();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id1,currentBrick,nextBrick,blocks,blocksColor,score0,score1});
    }

}

function checkMoveRight2()
{

    let check = true;
    for (let  c of currentBrick2.coordinates) 
    {
        if ((c.x + 1) >= 10 || blocks2[c.y][c.x + 1]) 
        {
            check = false;
            break;
        }
    }

    if (check) {
        currentBrick2.MoveRight();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id2,"currentBrick":currentBrick2,"nextBrick":nextBrick2,"blocks":blocks2,"blocksColor":blocksColor2,"score0":score1,"score1":score0});
    }

}

function nextBrick1() {
    currentBrick = new Brick(nextBrick.type);
    nextBrick.type = getRandomBrick();
}

function nextBrick21() {
    currentBrick2 = new Brick(nextBrick2.type);
    nextBrick2.type = getRandomBrick();
}
function checkRotation() 
{


    let testBrick = new Brick(currentBrick.type);
    for (let i = 0; i < 4; i++) 
    {
        testBrick.coordinates[i].y = currentBrick.coordinates[i].y;
        testBrick.coordinates[i].x = currentBrick.coordinates[i].x;
    }
    testBrick.Rotate();

    let check = true;
    for (let  c of testBrick.coordinates) 
    {
        if ((c.x) >= 10 || c.x < 0 || blocks[c.y][c.x]) {
            check = false;
            break;
        }
    }
    if (check) {
        currentBrick.Rotate();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id1,currentBrick,nextBrick,blocks,blocksColor,score0,score1});
    }
}

function checkRotation2() 
{


    let testBrick = new Brick(currentBrick2.type);
    for (let i = 0; i < 4; i++) 
    {
        testBrick.coordinates[i].y = currentBrick2.coordinates[i].y;
        testBrick.coordinates[i].x = currentBrick2.coordinates[i].x;
    }
    testBrick.Rotate();

    let check = true;
    for (let  c of testBrick.coordinates) 
    {
        if ((c.x) >= 10 || c.x < 0 || blocks[c.y][c.x]) {
            check = false;
            break;
        }
    }
    if (check) {
        currentBrick2.Rotate();
        parentPort.postMessage({"method":"gameTetrisInfo","id":id2,"currentBrick":currentBrick2,"nextBrick":nextBrick2,"blocks":blocks2,"blocksColor":blocksColor2,"score0":score1,"score1":score0});
    }
}

function checkLine() 
{
    let line=[];

    for (let i = 0; i < 20; i++) 
    {
        let flag = true;
        for (let j = 0; j < 10; j++) 
        {
            if (!blocks[i][j]) {
                flag = false;
                break;
            }
        }

        if (flag) 
        {
            line.push(i);
        }
    }
    score0+=scoreINc[line.length] ;
    line.forEach(element => {
        downingBlock(element);
    });
    
}



function checkLine2() 
{
    let line=[];

    for (let i = 0; i < 20; i++) 
    {
        let flag = true;
        for (let j = 0; j < 10; j++) 
        {
            if (!blocks2[i][j]) {
                flag = false;
                break;
            }
        }

        if (flag) 
        {
            line.push(i);
        }
    }
    score1+=scoreINc[line.length] ;
    line.forEach(element => {
        downingBlock2(element);
    });
    
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }
  

  // expected output: 0, 1 or 2
  function getRandomBrick() {
    return BrickType[getRandomInt(5)];
}
    Game();
    setInterval(takt,500);