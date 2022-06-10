// const {BroadcastChannel, workerData, parentPort, isMainThread } = require('worker_threads');

// var bc= new BroadcastChannel(workerData.name);
// let time = Date.now();
// let id1=workerData.clientId1;
// let id2=workerData.clientId2;
// let score1= workerData.score1;
let score0=0;


let blocks =[] ;
let blocksColor=[[]];
let currentBrick;
let nextBrick;
console.log("stary");

// const Colors = Enum({ RED: 'red', GREEN: 'green', BLUE: 'blue' });
// const color = Colors.RED;

// console.log('color === Colors.RED: ',   color === Colors.RED)     // true
// console.log('\'red\' === Colors.RED: ', 'red' === Colors.RED)   // false - строка не равна значению перечисления!!!
// console.log('color === Colors.GREEN: ', color === Colors.GREEN)   // false

// Colors.RED = 'red'; // не меняет значение в перечислении
let BrickType =[    "SQUARE","L_TYPE","LINE","T_TYPE","Z_TYPE"
]


let Color=["red","green","blue,purple"];
  
  


//   enum BrickType {
//     SQUARE,L_TYPE,LINE,T_TYPE,Z_TYPE;

//    Coordinate[][] state;

//     private static final Random RANDOM = new Random();

//     public static BrickType getRandomBrick() {
//         return BrickType.values()[RANDOM.nextInt(5)];
//     }
// }


    function  add( A,  B) 
    {
        return new {y:A.y + B.y,x:A.x + B.x};
    }

    function  sub( A,  B) {
        return new  {y:A.y - B.y, x:A.x - B.x};
    }

    function rotateAntiClock( X) {

        return new {y:-X.x,x: X.y};
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
            this.coordinates[i] = add(Coordinate.rotateAntiClock(before), center);
        }


    }
}

    


function Game() 
{

   
   

    for (let i = 0; i < 20; i++) 
    {
        blocks[i]=[];
        for (let j = 0; j < 10; j++) 
        {
            blocks[i][j] = false;
        }


    }

    if (nextBrick == null)
     {

        nextBrick =new Brick(getRandomBrick()) ;
        currentBrick = new Brick(nextBrick.type);
        nextBrick =new Brick(getRandomBrick()) ;
    }


}

function takt() {
    placing();
    currentBrick.Down();
    console.log(blocks);
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

function placing() {
    for (let  c of currentBrick.coordinates)
     {
        if ((c.y + 1) > 19 || blocks[c.y + 1][c.x] == true)
         {
            for (let  cInner of currentBrick.coordinates) 
            {
                blocks[cInner.y][cInner.x] = true;
            }
            checkLine();
            nextBrick();

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

    }

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
    score0=line.length*100;
    line.forEach(element => {
        downingBlock(element);
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
    takt();