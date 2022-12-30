var submit=document.getElementById('submitproperty');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  // const selected = document.querySelectorAll('classrecc option:checked');
  // console.log(selected);
  // console.log(document.querySelector('#classrecc').value);
  const selected = document.querySelectorAll('#propertyrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
  setproperty(values);
})
function getproperty(){
    fetch('/pr')
    .then(res=>res.json())
    .then(json=>get_proplist(json))
  }
  function setproperty(ans){
    const fd = new FormData()
    fd.append('reqpropname', ans);
    console.log([...fd])
    document.getElementById('loader1').style.visibility='visible';
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
    let table=document.getElementById('proptable');
    console.log(json);
    for(let i = 1;i<table.rows.length;){
      table.deleteRow(i);
    }
    let count=0;
    for (const key in json){
      if(count<20){
        const row=table.insertRow();
        const cls=row.insertCell(0);
        cls.innerHTML=key;
        const rec=row.insertCell(1);
        rec.innerHTML=json[key];
        count++
      }
    }
    console.log("done");
    document.getElementById('loader1').style.visibility='hidden';
  }