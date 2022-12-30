const odp=document.getElementById('odp');
odp.addEventListener('submit',function(e){
    e.preventDefault();
    const payload =new FormData(odp);
    console.log([...payload]);
    document.getElementById('loader1').style.visibility='visible';
    fetch('/odp', {
    method: 'POST',
    body: payload
    })
    .then(res => res.json())
    .then(json => set_odp_output(json))
    .catch(err => console.error(err));
})
function set_odp_output(json){
    console.log(json);
    let table=document.getElementById('odptable');
    for(let i = 1;i<table.rows.length;){
      table.deleteRow(i);
    }
    let count=0;
    let name=json[0];
    let iri=json[1];
    for(let i=0;i<name.length;i++){
        if(count<20){
        const row=table.insertRow();
        const cls=row.insertCell(0);
        cls.innerHTML=name[i];
        const rec=row.insertCell(1);
        rec.innerHTML=iri[i];
        count++
        }
    }
    console.log("done");
    document.getElementById('loader1').style.visibility='hidden';
}