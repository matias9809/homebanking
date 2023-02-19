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
                .then(response => 
                        console.log('signed in!!!')
                )
                .catch(err=>console.log(err))
        },
        singin(){
            axios.post('/api/clients',"firstName=pedro2&lastName=rodriguez&email=pedro@mindhub.com&password=pedro",{
            headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => console.log('registered'))
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")
