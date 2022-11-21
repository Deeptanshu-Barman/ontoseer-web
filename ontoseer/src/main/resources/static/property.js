var submit=document.getElementById('submitproperty');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  // const selected = document.querySelectorAll('classrecc option:checked');
  // console.log(selected);
  // console.log(document.querySelector('#classrecc').value);
  const selected = document.querySelectorAll('#classrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
})
function getproperty(){
    fetch('/pr')
    .then(res=>res.json())
    .then(json=>get_proplist(json))
  }
  function setproperty(ans){
    const fd = new FormData()
    fd.append('reqpropname', ans.value);
    console.log([...fd])
    fetch('/pr', {
    method: 'POST',
    body: fd
    })
    .then(res => res.json())
    .then(json => set_property_output(json))
    .catch(err => console.error(err));
    }
  function get_proplist(json){
    console.log(json);
    var dropdown=document.getElementById("propertyrecc");
    let option;
    dropdown.innerHTML="";
    for (let i=0;i<json.length;i++){
      option = document.createElement('option');
      option.text = json[i];
      option.value = json[i];
      dropdown.add(option);
    }
    document.multiselect('#propertyrecc').destroy();
    document.multiselect('#propertyrecc').setIsEnabled(true);
  }
  function set_property_output(json){
    let list=document.getElementById('Propertychange');
    list.innerHTML='';
    for (let i=0;i<json.length;i++){
      let li = document.createElement("li");
      li.innerText = json[i];
      list.appendChild(li);
    }
  }