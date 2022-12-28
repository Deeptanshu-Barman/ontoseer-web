var submit=document.getElementById('submitclass');
submit.addEventListener('click',()=>{
  console.log("Button clicked");
  const selected = document.querySelectorAll('#classrecc option:checked');
  const values = Array.from(selected).map(el => el.value);
  console.log(values);
  const fd = new FormData()
    fd.append('ClassList', values);
    console.log([...fd])
    fetch('/cr', {
    method: 'POST',
    body: fd
    })
    .then(res => res.json())
    .then(json => set_class_output(json))
    .catch(err => console.error(err));
})

  function set_class_output(json){
    let table=document.getElementById('Classtable');
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
  }