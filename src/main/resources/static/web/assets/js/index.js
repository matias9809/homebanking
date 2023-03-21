const { createApp } = Vue
createApp( {
    data(){
        return {

            email:"",
            password:"",
            firstname:"",
            lastname:"",
        }
    },
    created(){
    },
    methods: {
        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{
                    headers:{
                        'content-type':'application/x-www-form-urlencoded'
                    }
                })
                .then((response) => {
                    if(this.email == "ADMI@HOTMAIL.COM"){
                        location.href = '/createloan.html'
                    } 
                    else{
                        location.href = '/web/accounts.html'
                    }
                })
                .catch(err=>alert("Email or password was not correct"))
        },
        singin(){
            axios.post('/api/clients',`firstName=${this.firstname}&lastName=${this.lastname}&email=${this.email}&password=${this.password}`,{
            headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => this.login())
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")
