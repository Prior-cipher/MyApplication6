const http = require("http");
//const app = require("express")();

const websocketServer = require("websocket").server
const {Worker,BroadcastChannel} = require("worker_threads");

//const BroadcastChannel = require('broadcast-channel');
const httpServer = http.createServer();
httpServer.listen(7960, () => console.log("Listening.. on 7960"))

let clients = {};

let clientsAuth ={};
const games1 = [];
const games2 = [];
const games3 = [];
let currentGame1={};
let currentGame2={};
let currentGame3={};
let bclist={};
let bclist2={};
let bclist3={};
let game1i=0;
let game2i=0;
let i=0;




const wsServer = new websocketServer({
    "httpServer": httpServer
})

wsServer.on("request", request => 
{
    //connect
    const connection = request.accept(null, request.origin);
 
    connection.on("open", () => console.log("opened!"))
    connection.on("close", () => {
        console.log("closed!");
        

        

       let st=getKeyByValue(clients,connection);

       
       let stidgames1=games1.indexOf(st);
       let stidgames2=games2.indexOf(st);

      if(stidgames1!=-1)
      {
        games1.splice(stidgames1,1)
    }
        let k;
        for (const [key, value] of Object.entries(currentGame1)) 
        {
         if(value.id1==st )
         {
             //sendwin
             value.worker.terminate();
             clients[value.id2].send(JSON.stringify({"method":"canclegame1"}));
             k=key;
         }
         else if(value.id2==st){
             value.worker.terminate();
             clients[value.id1].send(JSON.stringify({"method":"canclegame1"}));
             k=key;
         }
      
         delete currentGame1.k;
        }
      if(stidgames2!=-1)
      {
        games2.splice(stidgames2,1);
        }
        let k1;
        for (const [key, value] of Object.entries(currentGame2)) 
        {
         if(value.id1==st )
         {
             //sendwin
             value.worker.terminate();
             clients[value.id2].send(JSON.stringify({"method":"surender2"}));
             k=key;
         }
         else if(value.id2==st)
         {
             value.worker.terminate();
             clients[value.id1].send(JSON.stringify({"method":"surender2"}));
             k1=key;
         }
      
         delete currentGame2.k1;
      }
      

    


    delete clients.st;
    
})
    connection.on("message", message => 
    {

      
            try {
                JSON.parse(message.utf8Data);
            } catch (e) 
            {
                //clients[clientId].connection.send("i say"+ message);
                console.log(message.utf8Data);
                return;
            }
            
        
        const result = JSON.parse(message.utf8Data);
        console.log(result.method);
        //I have received a message from the client
        //a user want to create a new game
       if (result.method === "surender2") 
        {
            try {
                currentGame2[result.gameID].worker.terminate();
                if(result.id== currentGame2[result.gameID].id1)
                {
                    //sendwin
                    clients[currentGame2[result.gameID].id2].send(JSON.stringify({"method":"surender2"}));
                    
                   
                }
                else if(result.id== currentGame2[result.gameID].id2)
                {
                   
                    clients[currentGame2[result.gameID].id1].send(JSON.stringify({"method":"surender2"}));
                   
                   }
                   
                   delete currentGame1.k;
                   delete clients.st;
                   
            } catch (error) 
            {
                
            }
            return;
           
       }
        
        if (result.method === "surender1") 
        {

           
            
           
            
           currentGame1[result.gameID].worker.terminate();
             if(result.id== currentGame1[result.gameID].id1)
             {
                 //sendwin
                 clients[currentGame1[result.gameID].id2].send(JSON.stringify({"method":"surender1"}));
                 
                
             }
             else if(result.id== currentGame1[result.gameID].id2)
             {
                
                 clients[currentGame1[result.gameID].id1].send(JSON.stringify({"method":"surender1"}));
                
                }
                
                delete currentGame1.k;
                delete clients.st;
                return;
        }
       
    
  
        if (result.method === "stopfind") 
        {
            games1.splice(games1.indexOf(result.id),1);
            return;
        } 
        if (result.method === "stopfind2") 
        {
            games2.splice(games2.indexOf(result.id),1);
            return;
        } 
        if (result.method === "iwantgame1") 
        { 
            games1.push(result.clientId);
           
        
            return;
           
        } 
        if (result.method === "iwantgame2") 
        { 
            games2.push(result.clientId);
           

            return;
           
        } 
     

        if (result.method === "gamePongInfo") 
        {
          
            console.log("recive:" +JSON.stringify(result))
try {
    bclist[result.gameID].postMessage({"id":result.id,"speed":result.speed});
} catch (error) {
    
}
        
         return;
         
        }
        if (result.method === "gameSnakeInfo") 
        {
          
            console.log("recive:" +JSON.stringify(result))
try {
    bclist2[result.gameID].postMessage({"id":result.id,"turn":result.turn,"gameID":result.gameID});
} catch (error) {
    
}
        
         return;
         
        }


  
 
        

    })
    console.log("new connection");
    clients[i] = connection;
    connection.send(JSON.stringify({
        "method":"start",
        "clientId":i
    }));
    i+=1;
   

})

setInterval(raspred,1000);





function raspred ()
{
if(games1.length>=2)
{
    clients[games1[0]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    clients[games1[1]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    // wsServer.connections[games1[0]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    // wsServer.connections[games1[1]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    
    bclist [game1i] = new BroadcastChannel('game1'+game1i);
    let worker = new Worker("./pong.js", {workerData: {clientId1:games1[0],clientId2:games1[1],"name":bclist[game1i].name}});
    
   

    worker.on("message", result => 
      {
        console.log("isend:" +JSON.stringify(result))
        if(result.method=="gamePongStat")
        {
            
         // try {
            //wsServer.connections[  result.id1 ].send(JSON.stringify(result));
            clients[ result.id1].send(JSON.stringify(result));
        //   } catch (error) 
        //   {
              
        //   }
            
           
        }
        else if(result.method=="endgame1")
        {
            clients[ result.id1].send(JSON.stringify(result));
            
        }
        
    });

      worker.on("error", error => {
          console.log(error);
      });

    worker.on("exit", exitCode =>
     {
          console.log(`It exited with code ${exitCode}`);
          });
         
        //   worker.on("close", exitCode =>
        //   {
        //        console.log(`It exited with code ${exitCode}`);
        //        });
        currentGame1[game1i]={id1:games1[0],id2:games1[1],"worker":worker};
               game1i++;
               games1.splice(0,2);
    }  

    if(games2.length>=2)
{
    clients[games2[0]].send(JSON.stringify({"method":"loadgame2","gameID":game2i}));
    clients[games2[1]].send(JSON.stringify({"method":"loadgame2","gameID":game2i}));
    // wsServer.connections[games1[0]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    // wsServer.connections[games1[1]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
    
    bclist2 [game2i] = new BroadcastChannel('game2'+game2i);
    let worker2 = new Worker("./snake.js", {workerData: {clientId1:games2[0],clientId2:games2[1],"name":bclist2[game2i].name}});
    //let worker = new Worker("./snake.js", {workerData: {clientId1:games2[0],clientId2:games2[0],"name":bclist2[game2i].name}});
    
   

    worker2.on("message", result => 
      {
        console.log("isend:" +JSON.stringify(result))
        if(result.method=="gameSnakeStat")
        {
            
         // try {
            //wsServer.connections[  result.id1 ].send(JSON.stringify(result));
            clients[ result.id1].send(JSON.stringify(result));
        //   } catch (error) 
        //   {
              
        //   }
            
           
        }
        else if(result.method=="endgame2")
        {
            clients[ result.id1].send(JSON.stringify(result));
            
        }
        
    });

      worker2.on("error", error => {
          console.log(error);
      });

    worker2.on("exit", exitCode =>
     {
          console.log(`It exited with code ${exitCode}`);
          });
         
  
        currentGame2[game2i]={id1:games2[0],id2:games2[1],"worker":worker2};
               game2i++;
               games2.splice(0,2);
    }  
        
        
    
}  


function getKeyByValue(object, value) {
    return Object.keys(object).find(key => object[key] === value);
  }



