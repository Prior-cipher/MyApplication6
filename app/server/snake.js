const {BroadcastChannel, workerData, parentPort, isMainThread } = require('worker_threads');

var bc= new BroadcastChannel(workerData.name);
let time = Date.now();
let id1=workerData.clientId1;
let id2=workerData.clientId2;

let score0=0;

let score1= 0;
 let h = [];
 let zmey = [[9, 0], [10, 0]];
 let zmey1 = [[9, 19], [10, 19]];

 let direction = "top";
 let direction1 = "top";


 let stat = false;
 let status1=true;

 let turRight=true;
 let turLeft= true;

 let turRight1=true;
 let turLeft1= true;


 bc.onmessage = event => 
 {
    if(event.data.id==id1)
    {
        if(event.data.turn){
            turnRight();
        } 
        else{
           
            turnLeft();
        }
        
    }
    else
    {
          if(event.data.turn){
            turnRight1();
        } 
        else{
            turnLeft1();
            
            
        }
    }
 }

 let t= setInterval(Game,1000);



    function getRndInteger( min,  max)
    {
        return  (Math.floor(Math.random() * (max - min + 1)) + min);
    }





    function setFood()
    {

         x = getRndInteger(0, 19);
         y = getRndInteger(0, 19);
        while (!chekIncludes(x, y) && !chekIncludes1(x, y))
        {
            x = getRndInteger(0, 19);
            y = getRndInteger(0, 19);
        }
        
        return  [x, y];
    }


    function chekIncludes( x,  y)
    {
         flag = false;
        
       for (let index = 0; index < zmey.length; index++) 
       {
        
        if (zmey[index][0] == y && zmey[index][1] == x)
        {

            return true;
        }
           
       }
       return false;
        

        
    }

    function chekIncludes1( x,  y)
    {
         flag = false;
        
       for (let index = 0; index < zmey1.length; index++) 
       {
        
        if (zmey1[index][0] == y && zmey1[index][1] == x)
        {

            return true;
        }
           
       }
       return false;
        

        
    }


    function chkImpact( )
    {
         
       
    
      for (let index1 = 0; index1 < zmey1.length; index1++) 
      {
        
          if (zmey[0][0] == zmey1[index1][0] && zmey[0][1] == zmey1[index1][1])
        {

            return true;
        }
      }
        
           
       
       return false;
        

        
    }

    function chkImpact1( )
    {
  
        for (let index1 = 0; index1 < zmey.length; index1++) 
        {
          
            if (zmey1[0][0] == zmey[index1][0] && zmey1[0][1] == zmey[index1][1])
          {
  
              return true;
          }
        }
          
             
         
         return false;
        

        
    }

    function CheckZmey()
    {
        for ( let i = 0; i < zmey.length - 1; i++)
        {
            for ( let  j = i + 1; j < zmey.length; j++)
            {

                if (zmey[i][0] == zmey[j][0] && zmey[i][1] == zmey[j][1]) 
                {

                    return false;
                }

            }

        }

        return true;
    }

    function CheckZmey1()
    {
        for ( let i = 0; i < zmey1.length - 1; i++)
        {
            for ( let  j = i + 1; j < zmey1.length; j++)
            {

                if (zmey1[i][0] == zmey1[j][0] && zmey1[i][1] == zmey1[j][1]) 
                {

                    return false;
                }

            }

        }

        return true;
    }





    function    Game()
    {

        turLeft=true;
        turRight=true;
        turRight1=true;
        turLeft1= true;
        if (!stat)
        {
            h = setFood();

            stat = true;

        }


    let pred = [zmey[0][0], zmey[0][1]];
    let pred1 = [zmey1[0][0], zmey1[0][1]];
        switch (direction)
        {
            case "top":
                pred[0] = pred[0] - 1;
                break;
            case "down":
                pred[0] = pred[0]+1;
                break;
            case "left":
                pred[1] = pred[1] - 1;
                break;
            case "right":
                pred[1] = pred[1] + 1;
                break;
            default:
                break;
        }
        switch (direction1)
        {
            case "top":
                pred1[0] = pred1[0] - 1;
                break;
            case "down":
                pred1[0] = pred1[0]+1;
                break;
            case "left":
                pred1[1] = pred1[1] - 1;
                break;
            case "right":
                pred1[1] = pred1[1] + 1;
                break;
            default:
                break;
        }
        if (pred[0] ==  h[0] &&pred[1] ==  h[1])
        {


            zmey.push([h[0],h[1]]);
            score0+=1000;

            h = setFood();

        }
        else    if (pred1[0] ==  h[0] &&pred1[1] ==  h[1])
        {


            zmey1.push([h[0],h[1]]);
            score1+=1000;
            h = setFood();

        }
        else if(chkImpact())
        {
                    status1=false;
                    clearInterval(t);
                    parentPort.postMessage({"method":"endgame2",id1,id2,"score1":score0,"win1":true}) ;
                    parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":false}) ;
                    process.exit(1);
        }
        else if(chkImpact1())
        {
                    status1=false;
                    clearInterval(t);
                    parentPort.postMessage({"method":"endgame2",id1,id2,"score1":score0,"win1":false}) ;
                    parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":true}) ;
                    process.exit(1);
        }
      
         else

            {
                if (pred[0] < 0 || pred[0] > 19 || pred[1] < 0 || pred[1] > 19 )
                {
                    status1=false;
                    clearInterval(t);
                    parentPort.postMessage({"method":"endgame2",id1,id2,"score1":score0,"win1":false}) ;
                    parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":true}) ;
                    process.exit(1);
                   
                   
                }

                if (pred1[0] < 0 || pred1[0] > 19 || pred1[1] < 0 || pred1[1] > 19 )
                {
                    status1=false;
                    clearInterval(t);

                    parentPort.postMessage({"method":"endgame2",id1,id2,"score1":score0,"win1":true}) ;
                    parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":false}) ;
                    process.exit(1);
                   
                   
                }


            for ( let index = zmey.length - 1; index > 0; index--)
            {

                zmey[index]= zmey[index - 1];

            }
            switch (direction)
            {
                case "top":
                    zmey[0]= [zmey[0][0] - 1, zmey[0][1]];
                    break;
                case "down":
                    zmey[0]= [zmey[0][0] + 1, zmey[0][1]];
                    break;
                case "left":
                    zmey[0]= [zmey[0][0], zmey[0][1] - 1];
                    break;
                case "right":
                    zmey[0]= [zmey[0][0], zmey[0][1] + 1];
                    break;
                default:
                    break;
            }

            for ( let index = zmey1.length - 1; index > 0; index--)
            {

                zmey1[index]= zmey1[index - 1];

            }
            switch (direction1)
            {
                case "top":
                    zmey1[0]= [zmey1[0][0] - 1, zmey1[0][1]];
                    break;
                case "down":
                    zmey1[0]= [zmey1[0][0] + 1, zmey1[0][1]];
                    break;
                case "left":
                    zmey1[0]= [zmey1[0][0], zmey1[0][1] - 1];
                    break;
                case "right":
                    zmey1[0]= [zmey1[0][0], zmey1[0][1] + 1];
                    break;
                default:
                    break;
            }
        }


        if ( !CheckZmey())
        {
            parentPort.postMessage({"method":"endgame2",id1,id2,score1,"win1":false}) ;
            parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":true}) ;
            clearInterval(t);
            process.exit(1);
        }
        if ( !CheckZmey1())
        {
            parentPort.postMessage({"method":"endgame2",id1,id2,score1,"win1":true}) ;
            parentPort.postMessage({"method":"endgame2","id1":id2,"id2":id1,score1,"win1":false}) ;
            clearInterval(t);
            process.exit(1);
        }
        parentPort.postMessage({"method":"gameSnakeStat",id1,zmey,zmey1,score1,h});
        parentPort.postMessage({"method":"gameSnakeStat","id1":id2,"zmey":zmey1,"zmey1":zmey,score1,h});

    }

    function turnRight()
{
        if(turRight){
            switch (direction) {
                case "top":
                    direction="right";
                    break;
                case "down":
                    direction="left";
                    break;
                case "left":
                    direction="top";
                    break;
                case "right":
                    direction="down";
                    break;
                default:
                    break;

            }
            if(!turLeft){
                turLeft=false;
                
            }
            else{
                turRight=false;
            }
    }
    }

    function turnLeft()
    {
        if(turLeft)
        {
            switch (direction)
            {
                case "top":
                    direction="left";
                    break;
                case "down":
                    direction="right";
                    break;
                case "left":
                    direction="down";
                    break;
                case "right":
                    direction="top";
                    break;
                default:
                    break;
            }
            if(!turRight)
                {
                    turRight=false;
                    
                }
                else{
                    turLeft=false;
                }
        }

    }
    function turnRight1()
    {
            if(turRight1)
            {
                switch (direction1) {
                    case "top":
                        direction1="right";
                        break;
                    case "down":
                        direction1="left";
                        break;
                    case "left":
                        direction1="top";
                        break;
                    case "right":
                        direction1="down";
                        break;
                    default:
                        break;
    
                }
                if(!turLeft1){
                    turLeft1=false;
                    
                }
                else{
                    turRight1=false;
                }
               
        }
        }
    
        function turnLeft1()
        {
            if(turLeft1)
            {
                switch (direction1)
                {
                    case "top":
                        direction1="left";
                        break;
                    case "down":
                        direction1="right";
                        break;
                    case "left":
                        direction1="down";
                        break;
                    case "right":
                        direction1="top";
                        break;
                    default:
                        break;
                }
                if(!turRight1)
                {
                    turRight1=false;
                    
                }
                else{
                    turLeft1=false;
                }
            }
    
        }

  
    