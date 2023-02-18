const { createApp } = Vue
createApp( {
    data(){
        return {

            email:"",
            password:""

        }
    },
    created(){
        this.login()
    },
    methods: {
        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{
                    headers:{
                        'content-type':'application/x-www-form-urlencoded'
                    }
                })
                .then(response => console.log('signed in!!!'))
                .catch(err=>console.log(err))
        },
    },

} ).mount("#app")
