var submit=document.getElementById('submithv');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  const ans1=document.getElementById("q1");
  const ans2=document.getElementById("q2");
  const ans3=document.getElementById("q3");
  const ans4=document.getElementById("q4");
  console.log(ans1.value);
  const fd= new FormData();
  fd.append('q1',ans1.value);
  fd.append('q2',ans2.value);
  fd.append('q3',ans3.value);
  fd.append('q4',ans4.value);
  console.log([...fd]);
})