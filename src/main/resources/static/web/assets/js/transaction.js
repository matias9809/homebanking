
const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            client:[],
            accountFilter:[],
            accounts:[],
            transactions:[]
            

        }
    },
    created(){
        axios.get(`/api/clients/current`)
        .then(response=>{
            this.client=response.data;
            this.accounts=response.data.account;
            console.log(this.accounts)
            let cadenaParametroUrl = location.search
            let parametros = new URLSearchParams(cadenaParametroUrl)
            let id= parametros.get("id")
            this.accountFilter =this.accounts.find(account => account.id == id);
            this.transactions=this.accountFilter.transaction.sort((a,b)=>a.id-b.id)
            console.log(this.transactions)
            console.log(this.accountFilter)
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
            
            this.transactions=this.accountFilter.transaction.sort((a,b)=>a.id-b.id)
            console.log(this.transactions)
            console.log(this.accountFilter)
        })
        .catch(err=>console.log(err))
        
    },
    methods: {
        load_data:function(){
            axios.get(`/api/clients/current`)
            .then(response=>{
                this.client=response.data;
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => response => 
                location.href = "./index.html")
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")