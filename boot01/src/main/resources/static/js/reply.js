/* 함수내에서 await를 사용하면
  함수에 async를 붙여준다.
  await==기다리지 않는다. 비동기
 */
async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`);

    // console.log(result);         // 1)
    // return result.data;          // 2)
    return result;                  // 3)
}

async function getList({bno, page, size, goLast}){
  const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}});

  // true면 최신 페이지(마지막 페이지)로 이동하라
  if(goLast){
    const total = result.data.total;
    const lastPage = parseInt(Math.ceil(total/size));

    return getList({bno:bno, page:lastPage, size:size});
  }

  return result.data;
}

async function addReply(replyObj){
  const response = await axios.post(`/replies/`, replyObj);
  return response.data;
}

async function getReply(rno){
  const response = await axios.get(`/replies/${rno}`);
  return response.data;
}

async function modifyReply(replyObj){
  const response = await axios.put(`/replies/${replyObj.rno}`, replyObj);
  return response.data;
}

async function removeReply(rno){
  const response = await axios.delete(`/replies/${rno}`);
  return response.data;
}