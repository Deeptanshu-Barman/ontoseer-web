var submit=document.getElementById('submitaxiom');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  // const selected = document.querySelectorAll('classrecc option:checked');
  // console.log(selected);
  // console.log(document.querySelector('#classrecc').value);
  const selected = document.querySelectorAll('#axiomrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
  setaxiom(values);
})
function getaxiom(){
    fetch('/ar')
    .then(res=>res.json())
    .then(json=>get_axiomlist(json))
  }
  function setaxiom(ans){
    const fd = new FormData()
    fd.append('reqaxiom', ans);
    console.log([...fd])
    fetch('/ar', {
    method: 'POST',
    body: fd
    })
    .then(res => res.json())
    .then(json => set_axiom_output(json))
    .catch(err => console.error(err));
    }
  function get_axiomlist(json){
    console.log(json);
    var dropdown=document.getElementById("axiomrecc");
    let option;
    dropdown.innerHTML="";
    for (let i=0;i<json.length;i++){
      option = document.createElement('option');
      option.text = json[i];
      option.value = json[i];
      dropdown.add(option);
    }
    document.multiselect('#axiomrecc').destroy();
    document.multiselect('#axiomrecc').setIsEnabled(true);
  }
  function set_axiom_output(json){
    let table=document.getElementById('axiomtable');
    console.log(json);
    for(let i = 1;i<table.rows.length;){
      table.deleteRow(i);
    }
    let count=0;
    for (const key in json){
      if(count<20){
        for (const cls in json[key]){
            const row=table.insertRow();
            const classname=row.insertCell(0);
            const recc=row.insertCell(1);
            const iri=row.insertCell(2);
            classname.innerHTML=key;
            recc.innerHTML=cls;
            iri.innerHTML=json[key][cls];
            count++;
        }
      }
    }
    console.log("done");
  }