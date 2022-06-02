const { workerData, parentPort, isMainThread } = require('worker_threads')
const time = Date.now();

// let c =0;
// while (true)
//  {
//   if (time + 200 <= Date.now()) 
//   {
   
//     break;
 
//  }
//  parentPort.postMessage(c++);
// }


parentPort.postMessage(c +"end");