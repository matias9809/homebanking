const { createApp } = Vue
createApp( {
    data(){
        return {

            email:"",
            password:"",
            firstname:"",
            lastname:"",
            singin_password:"",
            singin_email:""

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
                    if (response) {
                        location.href = "./accounts.html";
                    }
                })
                .catch(err=>alert("Email or password was not correct"))
        },
        singin(){
            axios.post('/api/clients',`firstName=${this.firstname}&lastName=${this.lastname}&email=${this.singin_email}&password=${this.singin_password}`,{
            headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => console.log('registered'))
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")
