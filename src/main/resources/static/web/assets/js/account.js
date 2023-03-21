const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            count:0,
            client:[],
            account:[],
            accountSelect: "",
            loans:[],
            cards:[],
            cards_credit:[],
            cards_debit:[],
            typeCard:"",
            colorCard:"",
            accountDelete:"",
            typeAccount:""
        }
    },
    created(){
        axios.get(`/api/clients/current`)
        .then(response=>{
            this.client=response.data;
            if(this.client.loan){
                this.loans=this.client.loan;
            }
            if(this.client.account){
                this.account=this.client.account
            }
            this.cards=response.data.cards
            this.cards_debit=this.cards.filter(cards=>cards.type=='DEBIT')
            this.cards_credit=this.cards.filter(cards=>cards.type=='CREDIT')
            console.log(this.loans)
        })
        .catch(err=>console.log(err))
      
    },
    methods: {
        load_data(){
            axios.get(`/api/clients/current`)
            .then(response=>{
                this.client=response.data;
                if(this.client.loan){
                    this.loans=this.client.loan;
                }
                if(this.client.account){
                    this.account=this.client.account
                }
                this.cards=response.data.cards
                this.cards_debit=this.cards.filter(cards=>cards.type=='DEBIT')
                this.cards_credit=this.cards.filter(cards=>cards.type=='CREDIT')
                console.log(this.loans)
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "./index.html")
            .catch(err=>console.log(err))
        },
        createAccount(){
                axios.post(`/api/clients/current/accounts?typeAccount=${this.typeAccount}`,{
                headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response =>{
                    alert("your account was created successful"),
                    this.load_data()
                    })
                .catch(err=>alert(err))
        },
        deleteAccount(){
            console.log(this.accountDelete);
            axios.patch(`/api/delete/account?number=${this.accountDelete}`,{
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(()=>{
                    alert("deleted account successful")
                    this.load_data();
                })
            .catch(err=>alert(err))
        }
    }

} ).mount("#app")