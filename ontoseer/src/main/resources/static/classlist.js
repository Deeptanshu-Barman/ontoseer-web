var submit=document.getElementById('submitclass');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  // const selected = document.querySelectorAll('classrecc option:checked');
  // console.log(selected);
  // console.log(document.querySelector('#classrecc').value);
  const selected = document.querySelectorAll('#classrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
})

function setclass(ans){
  console.log(document.querySelector(ans).value);
    // const fd = new FormData()
    // fd.append('reqclassname', ans.value);
    // console.log([...fd])
    // fetch('/cr', {
    // method: 'POST',
    // body: fd
    // })
    // .then(res => res.json())
    // .then(json => set_class_output(json))
    // .catch(err => console.error(err));
    }
  function set_class_output(json){
    let list=document.getElementById('Classchange');
    list.innerHTML='';
    for (let i=0;i<json.length;i++){
      let li = document.createElement("li");
      li.innerText = json[i];
      list.appendChild(li);
    }
  }