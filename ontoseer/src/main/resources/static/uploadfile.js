const input = document.getElementById('file');
document.getElementById('loader').style.visibility='hidden';
input.addEventListener('change', () => {
    var filePath = input.value;
    // Allowing file type
    var allowedExtensions =/(\.owl|\.rdf)$/i;
    if (!allowedExtensions.exec(filePath)) {
        alert('Invalid file type');
        fileInput.value = '';
    }
    else{
        uploadFile(input.files[0]);
    }
})
const uploadFile = file => {
// add the file to the FormData object
document.getElementById('loader').style.visibility='visible';
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
.catch(err => alert("Unable to Process your Ontology"));
}
const paste=document.getElementById('pasteform');
const pasteurl =document.getElementById('pasteurl');
pasteurl.addEventListener('submit',function(e){
    document.getElementById('loader').style.visibility='visible';
    e.preventDefault();
    const payload =new FormData(pasteurl);
    console.log([...payload]);
    fetch('/uploadurl', {
    method: 'POST',
    body: payload
    })
    .then(res => res.json())
    .then(json => getclass(json))
    .catch(err => alert("Unable to Process your Ontology"));
})
paste.addEventListener('submit',function(e){
    document.getElementById('loader').style.visibility='visible';
    e.preventDefault();
    const payload =new FormData(paste);
    console.log([...payload]);
    fetch('/uploadtext', {
    method: 'POST',
    body: payload
})
.then(res => res.json())
.then(json => getclass(json))
.catch(err => alert("Unable to Process your Ontology"));
})

function getclass(json){
    console.log(json);
    let dropdown=document.getElementById("classrecc");
    let option;
    dropdown.innerHTML="";
    for (let i=0;i<json.length;i++){
        option = document.createElement('option');
        option.text = json[i];
        option.value = json[i];
        dropdown.add(option);
    }
    document.getElementById('loader').style.visibility='hidden';
    document.getElementById("defaultresult").click();
    document.multiselect('#classrecc').destroy();
    document.multiselect('#classrecc').setIsEnabled(true);
}
