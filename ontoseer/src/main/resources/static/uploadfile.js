const input = document.getElementById('file');
input.addEventListener('change', () => {
    alert("clicked on submit")
    uploadFile(input.files[0])
})
const uploadFile = file => {
// add the file to the FormData object
const fd = new FormData()
fd.append('file', file)
console.log([...fd]);
// send `POST` request
fetch('/upload', {
    method: 'POST',
    body: fd
})
.then(res => res.json())
.then(json => getclass(json))
.catch(err => console.error(err));
}
const paste=document.getElementById('pasteform');
const pasteurl =document.getElementById('pasteurl');
pasteurl.addEventListener('submit',function(e){
    e.preventDefault();
    const payload =new FormData(pasteurl);
    console.log([...payload]);
    fetch('/uploadurl', {
    method: 'POST',
    body: payload
    })
    .then(res => res.json())
    .then(json => getclass(json))
    .catch(err => console.error(err));
})
paste.addEventListener('submit',function(e){
    e.preventDefault();
    const payload =new FormData(paste);
    console.log([...payload]);
    fetch('/uploadtext', {
    method: 'POST',
    body: payload
})
.then(res => res.json())
.then(json => getclass(json))
.catch(err => console.error(err));
})

function getclass(json){
    console.log(json);
    var dropdown=document.getElementById("classrecc");
    let option;
    dropdown.innerHTML="";
    for (let i=0;i<json.length;i++){
        option = document.createElement('option');
        option.text = json[i];
        option.value = json[i];
        dropdown.add(option);
    }
}
