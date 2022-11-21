const odp=document.getElementById('odp');
odp.addEventListener('submit',function(e){
    e.preventDefault();
    const payload =new FormData(odp);
    console.log([...payload]);
    fetch('/odp', {
    method: 'POST',
    body: payload
    })
    .then(res => res.json())
    .then(json => set_odp_output(json))
    .catch(err => console.error(err));
})
function set_odp_output(json){
    let list=document.getElementById('odprecommendation');
    list.innerHTML='';
    for (let i=0;i<json.length;i++){
        let li = document.createElement("li");
        li.innerText = json[i];
        list.appendChild(li);
    }
}