function custom_scroll() {
    window.scrollBy({ 
      top: 400, // could be negative value
      left: 0, 
      behavior: 'smooth' 
    });
  }
  function custom_scroll1() {
    window.scrollBy({ 
      top: 750, // could be negative value
      left: 0, 
      behavior: 'smooth' 
    });
  }
  document.getElementById("bt2").addEventListener("click", custom_scroll);