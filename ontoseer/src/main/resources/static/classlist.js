function setclass(ans){
    const fd = new FormData()
    fd.append('reqclassname', ans.value);
    console.log([...fd])
    fetch('/cr', {
    method: 'POST',
    body: fd
    })
    .then(res => res.json())
    .then(json => set_class_output(json))
    .catch(err => console.error(err));
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