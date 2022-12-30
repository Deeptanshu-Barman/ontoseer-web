var submit=document.getElementById('submitvocab');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  // const selected = document.querySelectorAll('classrecc option:checked');
  // console.log(selected);
  // console.log(document.querySelector('#classrecc').value);
  const selected = document.querySelectorAll('#vocabrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
  setvocab(values);
})
function getvocab(){
    fetch('/vr')
    .then(res=>res.json())
    .then(json=>get_vocablist(json))
  }
  function setvocab(ans){
    const fd = new FormData()
    fd.append('reqvocab', ans);
    console.log([...fd])
    document.getElementById('loader1').style.visibility='visible';
    fetch('/vr', {
    method: 'POST',
    body: fd
    })
    .then(res => res.json())
    .then(json => set_vocab_output(json))
    .catch(err => console.error(err));
    }
  function get_vocablist(json){
    console.log(json);
    var dropdown=document.getElementById("vocabrecc");
    let option;
    dropdown.innerHTML="";
    for (let i=0;i<json.length;i++){
      option = document.createElement('option');
      option.text = json[i];
      option.value = json[i];
      dropdown.add(option);
    }
    document.multiselect('#vocabrecc').destroy();
    document.multiselect('#vocabrecc').setIsEnabled(true);
  }
  function set_vocab_output(json){
    let table=document.getElementById('vocabtable');
    console.log(json);
    for(let i = 1;i<table.rows.length;){
      table.deleteRow(i);
    }
    let count=0;
    for (const key in json){
      if(count<20){
        for (const cls in json[key][0]){
            const row=table.insertRow();
            const classname=row.insertCell(0);
            const recc=row.insertCell(1);
            const iri=row.insertCell(2);
            classname.innerHTML=key;
            recc.innerHTML=json[key][0][cls];
            iri.innerHTML=json[key][1][cls]
            count++;
        }
      }
    }
    console.log("done");
    document.getElementById('loader1').style.visibility='hidden';
  }