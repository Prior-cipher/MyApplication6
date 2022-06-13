const http = require("http");
//const app = require("express")();

const websocketServer = require("websocket").server
const {Worker,BroadcastChannel} = require("worker_threads");

//const BroadcastChannel = require('broadcast-channel');
const httpServer = http.createServer();
httpServer.listen(56891, () => console.log(`Listening.. on ${process.env.APP_IP}:${process.env.APP_PORT}`))

let clients = {};

let clientsAuth ={};
const games1 = [];
const games2 = [];
const games3 = [];
const games4 = [];

let currentGame1={};
let currentGame2={};
let currentGame3={};
let currentGame4={};

let bclist={};
let bclist2={};
let bclist3={};
let bclist4={};

let game1i=0;
let game2i=0;
let game3i=0;
let game4i=0;
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
       let stidgames3=games3.indexOf(st);
       let stidgames4=games4.indexOf(st);
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

      if(stidgames3!=-1)
      {
        games3.splice(stidgames3,1);
        }
        let k2;
        for (const [key, value] of Object.entries(currentGame3)) 
        {
         if(value.id1==st )
         {
             //sendwin
             value.worker.terminate();
             clients[value.id2].send(JSON.stringify({"method":"surender3"}));
             k=key;
         }
         else if(value.id2==st)
         {
             value.worker.terminate();
             clients[value.id1].send(JSON.stringify({"method":"surender3"}));
             k1=key;
         }
      
         delete currentGame3.k2;
      }
      if(stidgames4!=-1)
      {
        games4.splice(stidgames4,1);
        }
        
        for (const [key, value] of Object.entries(currentGame4)) 
        {
         if(value.id1==st )
         {
             //sendwin
             value.worker.terminate();
             clients[value.id2].send(JSON.stringify({"method":"surender4"}));
             k=key;
         }
         else if(value.id2==st)
         {
             value.worker.terminate();
             clients[value.id1].send(JSON.stringify({"method":"surender4"}));
             k1=key;
         }
      
         delete currentGame4.k2;
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
                   
                   delete currentGame2.k;
                   delete clients.st;
                   
            } catch (error) 
            {
                
            }
            return;
           
       }
       if (result.method === "surender3") 
       {
           try {
               currentGame3[result.gameID].worker.terminate();
               if(result.id== currentGame3[result.gameID].id1)
               {
                   //sendwin
                   clients[currentGame3[result.gameID].id2].send(JSON.stringify({"method":"surender3"}));
                   
                  
               }
               else if(result.id== currentGame3[result.gameID].id2)
               {
                  
                   clients[currentGame3[result.gameID].id1].send(JSON.stringify({"method":"surender3"}));
                  
                  }
                  
                  delete currentGame3.k;
                  delete clients.st;
                  
           } catch (error) 
           {
               
           }
           return;
          
      }
      if (result.method === "surender4") 
      {
          try {
              currentGame4[result.gameID].worker.terminate();
              if(result.id== currentGame4[result.gameID].id1)
              {
                  //sendwin
                  clients[currentGame4[result.gameID].id2].send(JSON.stringify({"method":"surender4"}));
                  
                 
              }
              else if(result.id== currentGame4[result.gameID].id2)
              {
                 
                  clients[currentGame4[result.gameID].id1].send(JSON.stringify({"method":"surender4"}));
                 
                 }
                 
                 delete currentGame4.k;
                 delete clients.st;
                 
          } catch (error) 
          {
              
          }
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
        if (result.method === "stopfind3") 
        {
            games3.splice(games3.indexOf(result.id),1);
            return;
        } 

        if (result.method === "stopfind4") 
        {
            games4.splice(games4.indexOf(result.id),1);
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
     
        if (result.method === "iwantgame3") 
        { 
            games3.push(result.clientId);
           

            return;
           
        }
        if (result.method === "iwantgame4") 
        { 
            games4.push(result.clientId);
           

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
        if (result.method === "gameTetrisCommand") 
        {
          
            console.log("recive:" +JSON.stringify(result))
try {
    bclist3[result.gameID].postMessage(result);
} catch (error) {
    
}
        
         return;
         
        }
        if (result.method === "gameArcStat") 
        {
          
            console.log("recive:" +JSON.stringify(result))
try {
    bclist4[result.gameID].postMessage(result);
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
    if(games3.length>=2)
    {
        clients[games3[0]].send(JSON.stringify({"method":"loadgame3","gameID":game3i}));
        clients[games3[1]].send(JSON.stringify({"method":"loadgame3","gameID":game3i}));
        // wsServer.connections[games1[0]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
        // wsServer.connections[games1[1]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
        
        bclist3 [game3i] = new BroadcastChannel('game3'+game3i);
        let worker3 = new Worker("./tetris.js", {workerData: {clientId1:games3[0],clientId2:games3[1],"name":bclist3[game3i].name}});
        //let worker = new Worker("./snake.js", {workerData: {clientId1:games2[0],clientId2:games2[0],"name":bclist2[game2i].name}});
        
       
    
        worker3.on("message", result => 
          {
            console.log("isend:" +JSON.stringify(result))
            if(result.method=="gameTetrisInfo")
            {
                
              try {
                
                clients[ result.id].send(JSON.stringify(result));
               } catch (error) 
              {
                  
              }
                
               
            }
            else if(result.method=="endgame3")
            {
                clients[ result.id].send(JSON.stringify(result));
                
            }
            
        });
    
          worker3.on("error", error => {
              console.log(error);
          });
    
        worker3.on("exit", exitCode =>
         {
              console.log(`It exited with code ${exitCode}`);
              });
             
      
            currentGame3[game3i]={id1:games3[0],id2:games3[1],"worker":worker3};

                   game3i++;
                   games3.splice(0,2);
        }  


        if(games4.length>=2)
        {
            clients[games4[0]].send(JSON.stringify({"method":"loadgame4","gameID":game4i}));
            clients[games4[1]].send(JSON.stringify({"method":"loadgame4","gameID":game4i}));
            // wsServer.connections[games1[0]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
            // wsServer.connections[games1[1]].send(JSON.stringify({"method":"loadgame1","gameID":game1i}));
            
            bclist4 [game4i] = new BroadcastChannel('game4'+game4i);
            let worker4 = new Worker("./arcanoid.js", {workerData: {clientId1:games4[0],clientId2:games4[1],"name":bclist4[game4i].name}});
            //let worker = new Worker("./snake.js", {workerData: {clientId1:games2[0],clientId2:games2[0],"name":bclist2[game2i].name}});
            
           
        
            worker4.on("message", result => 
              {
                console.log("isend:" +JSON.stringify(result))
                if(result.method=="gameArcStat")
                {
                    
                  try {
                    
                    clients[ result.id].send(JSON.stringify(result));
                   } catch (error) 
                  {
                      
                  }
                    
                   
                }
                else if(result.method=="endgame4")
                {
                    clients[ result.id].send(JSON.stringify(result));
                    
                }
                
            });
        
            worker4.on("error", error => {
                  console.log(error);
              });
        
              worker4.on("exit", exitCode =>
             {
                  console.log(`It exited with code ${exitCode}`);
                  });
                 
          
                currentGame4[game4i]={id1:games4[0],id2:games4[1],"worker":worker4};
    
                       game4i++;
                       games4.splice(0,2);
            } 
        
    
}  


function getKeyByValue(object, value) {
    return Object.keys(object).find(key => object[key] === value);
  }



