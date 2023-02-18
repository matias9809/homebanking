
const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            client:undefined,
            accountFilter:undefined,
            accounts:undefined,
            

        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/1`)
        .then(response=>{
            this.client=response.data;
        })
        .catch(err=>console.log(err))
        axios.get(`http://localhost:8080/api/account`)
        .then(response=>{
            this.accounts=response.data;
            console.log(this.accounts)
            let cadenaParametroUrl = location.search
            let parametros = new URLSearchParams(cadenaParametroUrl)
            let id= parametros.get("id")
            this.accountFilter =this.accounts.find(account => account.id == id);
            console.log(this.accountFilter)
        })
        .catch(err=>console.log(err))
        
    },
    methods: {
        load_data:function(){
            axios.get(`http://localhost:8080/api/clients/1`)
            .then(response=>{
                this.client=response.data;
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        }
    },

} ).mount("#app")