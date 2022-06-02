// function runService(workerData) {

//     return new Promise((resolve, reject) => {
//       const worker = new Worker('./worker2.js', { workerData });
//       worker.on('message', resolve);
//       worker.on('error', reject);
//       worker.on('exit', (code) => {
//         if (code !== 0)
//           reject(new Error(`Worker stopped with exit code ${code}`));
//       })
//     })
//   }
  
//   async function run() {
//     //console.log("test");
//     const result1 = await runService('world')
//     //console.log(result1);
//   }
// run().catch(err => console.error(err))

const result = JSON.parse("startgame:1");
console.log(result.method);
